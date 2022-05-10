package com.xiting.a4e.ui.views;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlFindingStr;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.BapiBean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class BasicView {

	@Inject
	IWorkbench workbench;

	public static final String ID = "com.xiting.a4e.ui.basic";
	private TableViewer viewer;
	private Action contextMenuAction;

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Xiting Alchemist Results", message);
	}

	private void makeActions() {
		contextMenuAction = new Action("Context menu item") {
			public void run() {
				if (viewer instanceof TableViewer) {
					IStructuredSelection selection = ((TableViewer) viewer).getStructuredSelection();
					Object obj = selection.getFirstElement();
					showMessage("Context menu click detected on " + obj.toString());
				}
			}
		};
	}

	@Focus
	public void setFocus() {
		if (viewer != null)
			viewer.getControl().setFocus();
	}

	public void refresh() {
		setContents();
		viewer.refresh();
	}

	private void setContents() {
		BapiBean bean = AlchemistController.factory().getBean();
		if (bean.findings != null) {
			viewer.setInput(bean.findings);
		}
	}

	@PostConstruct
	public void createPartControl(Composite parent) {
		ArrayList<AlFindingStr> findings = AlchemistController.factory().getBean().findings ;
		if (findings == null)
			displayMessageInView(parent, "No Alchemist analysis has been run");
		else if (findings.isEmpty())
			displayMessageInView(parent, "No results from Alchemist analysis");
		else {
			displayFindings(parent);
		}
	}

	private void displayFindings(Composite parent) {
		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		createColumns(viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		setContents();
		makeActions();
		hookContextMenu();
	}

	private void displayMessageInView(Composite parent, String message) {
		new Label(parent, SWT.BORDER).setText(message);
	}

	private void createColumns(TableViewer viewer) {
		createColumn(viewer, "Pattern", AlFindingStr.PATTERN);
		createColumn(viewer, "Finding Id", AlFindingStr.FINDID);
		createColumn(viewer, "Is Finding Id Main?", AlFindingStr.FINDID_MAIN);
		createColumn(viewer, "Codepos Line", AlFindingStr.CODEPOS_LINE);
		createColumn(viewer, "Codepos Include", AlFindingStr.CODEPOS_INCLUDE);
		createColumn(viewer, "Object Name", AlObjectStr.NAME);
		createColumn(viewer, "Object Type", AlObjectStr.TYPE);
		createColumn(viewer, "Object Include", AlObjectStr.INCLUDE);
		createColumn(viewer, "Arbgb", AlFindingStr.ARBGB);
		createColumn(viewer, "Message Number", AlFindingStr.MSGNR);
		createColumn(viewer, "Message Type", AlFindingStr.MSGTY);
		createColumn(viewer, "Message", AlFindingStr.MESSAGE);
		createColumn(viewer, "Parameter 1", AlFindingStr.PARAM1);
		createColumn(viewer, "Parameter 2", AlFindingStr.PARAM2);
		createColumn(viewer, "Long paramter", AlFindingStr.PARAMLONG);
	}

	private void createColumn(TableViewer viewer, String headingText, String columnId) {
		Table table = viewer.getTable();
		TableViewerColumn patternCol = new TableViewerColumn(viewer, SWT.NONE);
		table.setSortColumn(patternCol.getColumn());
		patternCol.getColumn().setWidth(200);
		patternCol.getColumn().setText(headingText);
		patternCol.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String colText = "";
				if (element instanceof AlFindingStr) {
					Map<String, String> finding = ((AlFindingStr) element).returnAsMapString();
					colText = finding.get(columnId);
				}
				return colText;
			}
		});
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				BasicView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	protected void fillContextMenu(IMenuManager manager) {
		manager.add(contextMenuAction);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(contextMenuAction);
	}

	private void navigateToObject() {
//		IAdtCoreFactory factory = IAdtCoreFactory.eINSTANCE;
//		IAdtObjectReference objectReference = factory.createAdtObjectReference();
		// TODO work out what the references are - get an existing instance and check
		// field values in debug
	}
}
