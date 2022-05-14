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
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
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
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.sap.conn.jco.JCoException;
import com.xiting.a4e.backend.INavigator;
import com.xiting.a4e.backend.NavigatorFactory;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlCallstackStr;
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
	private Action doubleClickAction;

//	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
//		@Override
//		public String getColumnText(Object obj, int index) {
//			return getText(obj);
//		}
//
//		@Override
//		public Image getColumnImage(Object obj, int index) {
//			return getImage(obj);
//		}
//
//		@Override
//		public Image getImage(Object obj) {
//			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
//		}
//	}

//	private void showMessage(String message) {
//		MessageDialog.openInformation(viewer.getControl().getShell(), "Xiting Alchemist Results", message);
//	}

	private void makeActions() {
		contextMenuAction = new Action("Show callstack") {
			public void run() {
				if (viewer instanceof TableViewer) {
					CallstackRunner runner = new CallstackRunner();
					IStructuredSelection selection = ((TableViewer) viewer).getStructuredSelection();
					Object obj = selection.getFirstElement();
					if (obj instanceof AlFindingStr) {
						runner.openCallStackView((AlFindingStr) obj);
					}
				}

			}

		};
		doubleClickAction = new Action() {
			@Override
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				if (obj instanceof AlFindingStr) {
					AlFindingStr alFinding = (AlFindingStr) obj;
					try {
						navigateToObject(alFinding);
					} catch (JCoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
		ArrayList<AlFindingStr> findings = AlchemistController.factory().getBean().findings;
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
		hookDoubleClickAction();
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});

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
					if (isImageLabel(columnId))
						return null;
					else {
						Map<String, String> finding = ((AlFindingStr) element).returnAsMapString();
						colText = finding.get(columnId);
					}
					
				}
				return colText;
			}
			@Override
			public Image getImage(Object element) {
				if (element instanceof AlFindingStr) {
					if (isImageLabel(columnId))
					{
						AlFindingStr finding = (AlFindingStr) element;
						switch (finding.msgty) {
						case "W" :
						   return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
						case "E" :
							   return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
							   default : 
								   return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);
						}
					}
					else return null;
			}return null;
			}

			private boolean isImageLabel(String columnId) {
				return (columnId == AlFindingStr.MSGTY);
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

	private void navigateToObject(AlFindingStr selectedObject) throws JCoException {
		INavigator navigator = NavigatorFactory.get();
		navigator.jump_to(selectedObject.object, selectedObject.codeposLine);
	}

}
