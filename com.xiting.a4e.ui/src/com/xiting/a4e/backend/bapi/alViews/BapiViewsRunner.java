package com.xiting.a4e.backend.bapi.alViews;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoTable;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.BapiViewsBean;
import com.xiting.a4e.ui.A4eUiTexts;
import com.xiting.a4e.ui.views.ViewsManager;

public class BapiViewsRunner implements IBapiViewsRunner {

	private Display sync;

	@Override
	public void run(JCoDestination destination) {
		AlchemistController controller = AlchemistController.factory();
		BapiViewsBean bean = controller.getViewsBean();
		JCoTable callStackTable = controller.getCallStackTable();
		JCoTable findingsTable = controller.getFindingsTable();
		startJob(destination, bean, callStackTable, findingsTable);
	}

	private void startJob(JCoDestination destination, BapiViewsBean bean, JCoTable callStackTable,
			JCoTable findingsTable) {
		if (destination == null)
			return;
		sync = Display.getDefault();
		String jobMessage = A4eUiTexts.getString("BapiViews_Running");
		Job job = new Job(jobMessage) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				AlBapiViewsRunner runner;
				try {
					runner = new AlBapiViewsRunner(bean, destination, callStackTable, findingsTable);
					runner.run();
					updateViews();
				} catch (JCoException e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.SHORT);
		job.schedule();

	}

	private void updateViews() {
		sync.asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					ViewsManager.get().setViewOpened(ViewsManager.AUTH_CHECKS_ID);
					ViewsManager.get().updateViews();
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
