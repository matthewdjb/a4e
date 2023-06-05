package com.xiting.a4e.ui.views;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.sap.conn.jco.JCoException;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlFindingStr;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.BapiBean;
import com.xiting.a4e.model.structures.NavigationTarget;
import com.xiting.a4e.ui.A4eUiTexts;

class BasicView implements IAlchemistView {

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;
	private Action doubleClickAction;

	private ArrayList<Action> contextMenuActions;

	private void makeActions() {
		contextMenuActions = new ArrayList<>();
		ViewsManager.get().addViewsToContextMenu(viewer, contextMenuActions, ViewsManager.BASIC_ID);

		doubleClickAction = new Action() {
			@Override
			public void run() {
				try {
					IStructuredSelection selection = viewer.getStructuredSelection();
					Object obj = selection.getFirstElement();
					if (obj instanceof AlFindingStr) {
						AlFindingStr finding = (AlFindingStr) obj;
						NavigationTarget target = new NavigationTarget(finding.object, finding.codeposLine);
						ViewsManager.navigateToObject(target);
					}
				} catch (JCoException e) {
					e.printStackTrace();
				}
			}

		};
	}

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
		BapiBean bean = AlchemistController.factory().getBean();
		if (bean.findings != null) {
			viewer.setInput(bean.findings);
		}
	}

	@PostConstruct
	public void createPartControl(Composite parent) { // NO_UCD (unused code)
		ArrayList<AlFindingStr> findings = AlchemistController.factory().getBean().findings;
		if (findings == null)
			displayMessageInView(parent, A4eUiTexts.getString("NoAnalysis")); //$NON-NLS-1$
		else if (findings.isEmpty())
			displayMessageInView(parent, A4eUiTexts.getString("NoResults")); //$NON-NLS-1$
		else {
			displayFindings(parent);
		}
		ViewsManager.get().setViewOpened(ViewsManager.BASIC_ID);
	}

	private void displayFindings(Composite parent) {
		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		createColumns();
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setHeaderForeground(AlchemistController.XITING_COLOUR);
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

	private void createColumns() {
		createColumn(AlFindingStr.PATTERN);
		createColumn(AlFindingStr.FINDID);
		createColumn(AlFindingStr.FINDID_MAIN);
		createColumn(AlFindingStr.CODEPOS_LINE);
		createColumn(AlFindingStr.CODEPOS_INCLUDE);
		createColumn(AlObjectStr.NAME);
		createColumn(AlObjectStr.TYPE);
		createColumn(AlObjectStr.INCLUDE);
		createColumn(AlFindingStr.ARBGB);
		createColumn(AlFindingStr.MSGNR);
		createColumn(AlFindingStr.MSGTY);
		createColumn(AlFindingStr.MESSAGE);
		createColumn(AlFindingStr.PARAM1);
		createColumn(AlFindingStr.PARAM2);
		createColumn(AlFindingStr.PARAMLONG);
	}

	private void createColumn(String columnId) {
		String headingText = AlFindingStr.getColumnDescription(columnId);
		ScopedPreferenceStore preferences = AlchemistController.factory().getPreferenceStore();
		if (preferences.getBoolean(columnId)) {
			Table table = viewer.getTable();
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			table.setSortColumn(column.getColumn());
			column.getColumn().setWidth(200);
			column.getColumn().setText(headingText);
			column.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					String colText = columnId;
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
						if (isImageLabel(columnId)) {
							AlFindingStr finding = (AlFindingStr) element;
							switch (finding.msgty) {
							case "W": //$NON-NLS-1$
								return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
							case "E": //$NON-NLS-1$
								return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
							default:
								return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);
							}
						} else
							return null;
					}
					return null;
				}

				private boolean isImageLabel(String columnId) {
					return (columnId == AlFindingStr.MSGTY);
				}
			});
		}
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
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

	private void fillContextMenu(IMenuManager manager) {
		for (Action contextMenuAction : contextMenuActions)
			manager.add(contextMenuAction);
	}

	@PreDestroy
	public void setViewClosed() { // NO_UCD (unused code)
		ViewsManager.get().setViewClosed(ViewsManager.BASIC_ID);
	}

}
