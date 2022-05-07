package com.ixiting.a4e.backend;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;
import com.sap.adt.project.IAdtCoreProject;
import com.sap.adt.project.IProjectProvider;
import com.xiting.a4e.model.AbapAdtTypes;
import com.xiting.a4e.model.Adt2AbapParserFactory;
import com.xiting.a4e.model.IAdt2AbapParser;
import com.xiting.a4e.model.structures.*;

import com.sap.adt.project.ui.util.ProjectUtil;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;

public class AlchemistRunner implements IAlchemistRunner {

	@Override
	public void run(MultiPageEditorPart editor) {
		IProject project = null;
		if (editor instanceof IProjectProvider) {
			IProjectProvider projectProvider = (IProjectProvider) editor;
			project = projectProvider.getProject();
		}
		if (project == null) {
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "No selection",
					"No ABAP Project selected");
		} else {
			ArrayList<AlObjectStr> items = new ArrayList<AlObjectStr>();
			IAdt2AbapParser parser = Adt2AbapParserFactory.getInstance(editor);
			AlObjectStr item = new AlObjectStr();
			item.name = parser.getName();
			item.type = parser.getType();
			System.out.println(item.name);
			System.out.println(item.type);
			items.add(item);
			if (!items.isEmpty())
				try {
					callBapi(getDestinationFromProject(project), items);
				} catch (JCoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	private void callBapi(JCoDestination destinationFromProject, ArrayList<AlObjectStr> items) {
		BapiBean bean = new BapiBean();
		AlchemistController.factory().setBean(bean);
		setScopeParameter(bean);
		setItems(bean, items);
		setPatternsParameter(bean);
		startJob(destination, bean);
	}
	private void startJob(JCoDestination destination, BapiBean bean) {
		Job job = new Job("Xiting Alchemist running...") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IStatus status = Status.OK_STATUS;
				AlBapiInspRunAdhocRunner runner;
				try {
					runner = new AlBapiInspRunAdhocRunner(bean, destination);
					runner.run();
					Display.getDefault().asyncExec(() -> {
						try {
							IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
									.findView(BasicView.ID);
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(BasicView.ID);
							if (view instanceof BasicView)
								((BasicView) view).refresh();
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					});
				} catch (JCoException e) {
					e.printStackTrace();
					monitor.done();
					status = Status.CANCEL_STATUS;
				}
				monitor.done();
				return status;
			}
		};
		job.setPriority(Job.SHORT);
		job.schedule(); 
	}
	@Override
	public void run(ISelection selection) {
		IProject project = ProjectUtil.getActiveAdtCoreProject(selection, null, null,
				IAdtCoreProject.ABAP_PROJECT_NATURE);
		if (selection instanceof ITreeSelection) {
			ArrayList<AlObjectStr> items = getItemsFromSelection((ITreeSelection) selection);
			if (!items.isEmpty())
				callBapi(getDestinationFromProject(project), items);
			else
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Unsupported Type",
						"No object support by Alchemist was selected");

		}
	}

	private JCoDestination getDestinationFromProject(IProject project) throws JCoException {
		String destinationId = com.sap.adt.project.AdtCoreProjectServiceFactory.createCoreProjectService()
				.getDestinationId(project);
		return JCoDestinationManager.getDestination(destinationId);
	}

	private ArrayList<AlObjectStr> getItemsFromSelection(ITreeSelection treeSelection) {
		ArrayList<AlObjectStr> items = new ArrayList<AlObjectStr>();
		TreePath[] paths = treeSelection.getPaths();
		for (TreePath path : paths) {
			IAdt2AbapParser parser = Adt2AbapParserFactory.getInstance(path);
			AlObjectStr item = new AlObjectStr();
			item.name = parser.getName();
			item.type = parser.getType();
			if (AbapAdtTypes.isSupported(item.type)) {
				items.add(item);
			} else {
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Unsupported Type",
						item.type + " is not supported by Alchemist");
			}
		}
		return items;
	}
}
