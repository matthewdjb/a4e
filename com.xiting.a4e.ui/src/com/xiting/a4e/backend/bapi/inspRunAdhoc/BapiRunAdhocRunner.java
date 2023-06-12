package com.xiting.a4e.backend.bapi.inspRunAdhoc;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.BapiBean;
import com.xiting.a4e.ui.A4eUiTexts;
import com.xiting.a4e.ui.views.ViewsManager;

class BapiRunAdhocRunner implements IBapiRunAdhocRunner {

	private Display sync;
	private AlchemistController controller;

	@Override
	public void run(JCoDestination destination, ArrayList<AlObjectStr> items) {
		BapiBean bean = new BapiBean();
		ViewsManager.reset();
		controller = AlchemistController.factory_new();
		controller.setBean(bean);
		controller.setDestination(destination);
		CallingParameterHandler callingParameterHandler = new CallingParameterHandler();
		callingParameterHandler.setScopeParameter(bean);
		callingParameterHandler.setItems(bean, items);
		callingParameterHandler.setPatternsParameter(bean);
		startJob(destination, bean);
	}

	private void startJob(JCoDestination destination, BapiBean bean) {
		sync = Display.getDefault();
		String jobMessage = A4eUiTexts.getString("BapiRunAdhocRunner_Running"); //$NON-NLS-1$
		Job job = new Job(jobMessage) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				AlBapiInspRunAdhocRunner runner;
				try {
					runner = new AlBapiInspRunAdhocRunner(bean, destination);
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
					ViewsManager.get().updateViews();
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
