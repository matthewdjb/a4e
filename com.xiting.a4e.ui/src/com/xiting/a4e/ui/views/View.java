package com.xiting.a4e.ui.views;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class View {

	protected TableViewer viewer;

	protected static void resizeTable(Table table) {
		for (TableColumn column : table.getColumns())
			column.pack();
	}

	protected void displayMessageInView(Composite parent, String message) {
		new Label(parent, SWT.BORDER).setText(message);
	}

	protected void fillContextMenu(IMenuManager manager, ArrayList<Action> contextMenuActions) {
		for (Action contextMenuAction : contextMenuActions)
			manager.add(contextMenuAction);
	}

	protected void initialiseViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
	}
}
