package com.xiting.a43.backend.bapi.inspRunAdhoc;

import java.util.ArrayList;

import javax.inject.Inject;

import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.xiting.a43.backend.iBapiRunAdhocRunner;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.BapiBean;
import com.xiting.a4e.ui.views.BasicView;

public class BapiRunAdhocRunner implements iBapiRunAdhocRunner {

	private Display sync;

//	@Inject
//	UISynchronize sync;

	@Override
	public void run(JCoDestination destination, ArrayList<AlObjectStr> items) {
		BapiBean bean = new BapiBean();
		AlchemistController.factory().setBean(bean);
		CallingParameterHandler callingParameterHandler = new CallingParameterHandler();
		callingParameterHandler.setScopeParameter(bean);
		callingParameterHandler.setItems(bean, items);
		callingParameterHandler.setPatternsParameter(bean);
		startJob(destination, bean);
	}

	private void startJob(JCoDestination destination, BapiBean bean) {
		sync = Display.getDefault();
//		Job job = Job.create("Xiting Alchemist running...", (ICoreRunnable) monitor -> {
//			AlBapiInspRunAdhocRunner runner;
//			try {
//				runner = new AlBapiInspRunAdhocRunner(bean, destination);
//				runner.run();
//				updateView();
//			} catch (JCoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
		Job job = new Job("Xiting Alchemist running...") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				AlBapiInspRunAdhocRunner runner;
				try {
					runner = new AlBapiInspRunAdhocRunner(bean, destination);
					runner.run();
					updateView();
				} catch (JCoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.SHORT);
		job.schedule();
	}

	private void updateView() {
		sync.asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.findView(BasicView.ID);
					if (view != null)
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(BasicView.ID);
					if (view instanceof BasicView)
						((BasicView) view).refresh();
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
