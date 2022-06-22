package com.xiting.a4e.ui.views;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbench;

import com.sap.conn.jco.JCoException;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.CallStack;
import com.xiting.a4e.model.structures.BapiBean;
import com.xiting.a4e.model.structures.CallStackStructure;
import com.xiting.a4e.ui.A4eUiPlugin;
import com.xiting.a4e.ui.A4eUiTexts;

class CallStackView implements IAlchemistView {

	@Inject
	IWorkbench workbench;
	private TreeViewer viewer;
	private CallStack callStack;
	private BapiBean bean;
	private ArrayList<Action> contextMenuActions;
	private Action doubleClickAction;

	@Focus
	public void setFocus() { // NO_UCD (unused code)
		if (viewer != null)
			viewer.getControl().setFocus();
	}

	@PostConstruct
	public void createPartControl(Composite parent) { // NO_UCD (unused code)
		callStack = AlchemistController.factory().getBean().callStack;
		if (callStack == null)
			displayMessageInView(parent, A4eUiTexts.getString("NoAnalysis")); //$NON-NLS-1$
		else if (callStack.isEmpty())
			displayMessageInView(parent, A4eUiTexts.getString("NoResults")); //$NON-NLS-1$
		else {
			displayCallstack(parent);
		}
		ViewsManager.get().setViewOpened(ViewsManager.CALL_STACK_ID);
	}

	private void displayCallstack(Composite parent) {
		viewer = new TreeViewer(parent);
		viewer.setContentProvider(new CallStackDataProvider());
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setHeaderForeground(AlchemistController.XITING_COLOUR);
		TreeViewerColumn descriptionColumn = new TreeViewerColumn(viewer, SWT.NONE);
		descriptionColumn.getColumn().setWidth(500);
		descriptionColumn.getColumn().setText(A4eUiTexts.getString("CallStackView.withFindings")); //$NON-NLS-1$
		descriptionColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof CallStackStructure)
					return ((CallStackStructure) element).description;
				return ""; //$NON-NLS-1$
			}

			@Override
			public Image getImage(Object element) {
				return  A4eUiPlugin.getIcon(((CallStackStructure) element).object.type);
			}
		});	
		setContents();
		GridLayoutFactory.fillDefaults().generateLayout(parent);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
	}

	private void setContents() {
		bean = AlchemistController.factory().getBean();
		viewer.setInput(bean.callStack);
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});

	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				CallStackView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);

	}

	private void fillContextMenu(IMenuManager manager) {
		for (Action contextMenuAction : contextMenuActions)
			manager.add(contextMenuAction);
	}

	private void displayMessageInView(Composite parent, String message) {
		new Label(parent, SWT.BORDER).setText(message);
	}

	@Override
	public void refresh() {
		setContents();
		viewer.refresh();
	}

	private void makeActions() {
		contextMenuActions = new ArrayList<>();
		ViewsManager.get().addViewsToContextMenu(viewer, contextMenuActions, ViewsManager.CALL_STACK_ID);

		doubleClickAction = new Action() {
			@Override
			public void run() {
				try {
					IStructuredSelection selection = viewer.getStructuredSelection();
					Object obj = selection.getFirstElement();
					if (obj instanceof CallStackStructure) {
						CallStackStructure node = (CallStackStructure) obj;
						ViewsManager.navigateToObject(node.target);
					}
				} catch (JCoException e) {
					e.printStackTrace();
				}
			}

		};
	}

	@PreDestroy
	public void setViewClosed() { // NO_UCD (unused code)
		ViewsManager.get().setViewClosed(ViewsManager.CALL_STACK_ID);
	}

}