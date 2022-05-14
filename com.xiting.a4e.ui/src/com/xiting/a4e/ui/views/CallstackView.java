package com.xiting.a4e.ui.views;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;

import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlCallstackStr;

public class CallstackView {

	@Inject
	IWorkbench workbench;
	public static final String ID = "com.xiting.a4e.ui.callstack";
	private TreeViewer viewer;

	@Focus
	public void setFocus() {
		if (viewer != null)
			viewer.getControl().setFocus();
	}

	@PostConstruct
	public void createPartControl(Composite parent) {
		ArrayList<AlCallstackStr> callstack = AlchemistController.factory().getBean().callstack;
		if (callstack == null)
			displayMessageInView(parent, "No Alchemist analysis has been run");
		else if (callstack.isEmpty())
			displayMessageInView(parent, "No results from Alchemist analysis");
		else {
			displayCallstack(parent);
		}
	}

	private void displayCallstack(Composite parent) {
		// TODO Auto-generated method stub

	}

	private void displayMessageInView(Composite parent, String message) {
		new Label(parent, SWT.BORDER).setText(message);
	}
}
