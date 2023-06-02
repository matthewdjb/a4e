package com.xiting.a4e.ui.views;

import java.util.ArrayList;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.IWorkbench;

import com.xiting.a4e.backend.bapi.alViews.BapiViewsFactory;
import com.xiting.a4e.model.structures.views.AuthBasicViewBean;
import com.xiting.a4e.model.structures.views.Views;


public class AuthBasicView implements IAlchemistView  {

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;
	private Action doubleClickAction;

	private ArrayList<Action> contextMenuActions;

	@Focus
	public void setFocus() { // NO_UCD (unused code)
		if (viewer != null)
			viewer.getControl().setFocus();
	}
	@Override
	public void refresh() {
		setContents();
		viewer.refresh();
	}

	private void setContents() {
		AuthBasicViewBean viewBean = (AuthBasicViewBean)Views.AUTH_BASIC.getBean();
        if (viewBean.IsEmpty()) {
        	BapiViewsFactory.getRunner().run(null, null, null);
        } else
        {
        	viewer.setInput(viewBean);
        }
	}

}
