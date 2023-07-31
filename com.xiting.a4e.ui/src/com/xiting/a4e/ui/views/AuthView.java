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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.sap.conn.jco.JCoException;
import com.xiting.a4e.backend.bapi.alViews.BapiViewsFactory;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlAuthCheckStr;
import com.xiting.a4e.model.structures.BapiViewsBean;
import com.xiting.a4e.model.structures.NavigationTarget;
import com.xiting.a4e.ui.A4eUiPlugin;
import com.xiting.a4e.ui.A4eUiTexts;

public class AuthView extends View implements IAlchemistView {

	private static final String ERROR = "@%C@";
	private static final String WARNING = "@5D@";

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

	AuthView() {
		AlchemistController controller = AlchemistController.factory();
		BapiViewsBean viewsBean = controller.getViewsBean();
		if (viewsBean.authChecks == null) {
			viewsBean.authCheckFlag = true;
			BapiViewsFactory.getRunner().run(controller.getDestination());
		}
	}

	@Override
	public void refresh() {
		setContents();
		viewer.refresh();
	}

	private void setContents() {
		AlchemistController controller = AlchemistController.factory();
		BapiViewsBean viewsBean = controller.getViewsBean();
		viewer.setInput(viewsBean.authChecks);
	}

	@PostConstruct
	public void createPartControl(Composite parent) { // NO_UCD (unused code)
		ArrayList<AlAuthCheckStr> authChecks = AlchemistController.factory().getViewsBean().authChecks;
		if (authChecks == null)
			displayMessageInView(parent, A4eUiTexts.getString("NoAnalysis")); //$NON-NLS-1$
		else if (authChecks.isEmpty())
			displayMessageWithContext(parent, A4eUiTexts.getString("NoResults")); //$NON-NLS-1$
		else {
			displayAuthChecks(parent);
		}
		ViewsManager.get().setViewOpened(ViewsManager.AUTH_CHECKS_ID);
	}

	private void displayMessageWithContext(Composite parent, String message) {
		Label label = new Label(parent, SWT.BORDER);
		label.setText(message);
		contextMenuActions = new ArrayList<>();
		ViewsManager.get().addViewsToContextMenu(label, contextMenuActions, ViewsManager.MISSING_AUTH_CHECKS);
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				AuthView.this.fillContextMenu(manager, contextMenuActions);
			}
		});
		label.setMenu(menuMgr.createContextMenu(label));
	}

	private void displayAuthChecks(Composite parent) {
		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		createColumns();
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setHeaderForeground(AlchemistController.XITING_COLOUR);
		setContents();
		resizeTable(table);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
	}

	private void createColumns() {
		createColumn(AlAuthCheckStr.LINE);
		createColumn(AlAuthCheckStr.SCOPETYPE);
		createColumn(AlAuthCheckStr.OBJECT);
		int maxField = AlchemistController.factory().getViewsBean().maxAuthCheckFields;
		switch (maxField) {
		case 10:
			createColumn(AlAuthCheckStr.FIELD10);
			createColumn(AlAuthCheckStr.VAL10);
			createColumn(AlAuthCheckStr.FLAG10);
		case 9:
			createColumn(AlAuthCheckStr.FIELD09);
			createColumn(AlAuthCheckStr.VAL09);
			createColumn(AlAuthCheckStr.FLAG09);
		case 8:
			createColumn(AlAuthCheckStr.FIELD08);
			createColumn(AlAuthCheckStr.VAL08);
			createColumn(AlAuthCheckStr.FLAG08);
		case 7:
			createColumn(AlAuthCheckStr.FIELD07);
			createColumn(AlAuthCheckStr.VAL07);
			createColumn(AlAuthCheckStr.FLAG07);
		case 6:
			createColumn(AlAuthCheckStr.FIELD06);
			createColumn(AlAuthCheckStr.VAL06);
			createColumn(AlAuthCheckStr.FLAG06);
		case 5:
			createColumn(AlAuthCheckStr.FIELD05);
			createColumn(AlAuthCheckStr.VAL05);
			createColumn(AlAuthCheckStr.FLAG05);
		case 4:
			createColumn(AlAuthCheckStr.FIELD04);
			createColumn(AlAuthCheckStr.VAL04);
			createColumn(AlAuthCheckStr.FLAG04);
		case 3:
			createColumn(AlAuthCheckStr.FIELD03);
			createColumn(AlAuthCheckStr.VAL03);
			createColumn(AlAuthCheckStr.FLAG03);
		case 2:
			createColumn(AlAuthCheckStr.FIELD02);
			createColumn(AlAuthCheckStr.VAL02);
			createColumn(AlAuthCheckStr.FLAG02);
		case 1:
			createColumn(AlAuthCheckStr.FIELD01);
			createColumn(AlAuthCheckStr.VAL01);
			createColumn(AlAuthCheckStr.FLAG01);
		default:
		}

	}

	private void createColumn(String columnId) {
		String headingText = AlAuthCheckStr.getColumnDescription(columnId);
		ScopedPreferenceStore preferences = AlchemistController.factory().getPreferenceStore();
		if (selectedInPreferences(columnId, preferences)) {
			Table table = viewer.getTable();
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			table.setSortColumn(column.getColumn());
			column.getColumn().setWidth(200);
			column.getColumn().setText(headingText);
			column.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					String colText = columnId;
					if (element instanceof AlAuthCheckStr) {
						if (isImageLabel(columnId))
							return null;
						else {
							Map<String, String> authCheck = ((AlAuthCheckStr) element).returnAsMapString();
							colText = authCheck.get(columnId);
						}
					}
					return colText;
				}

				@Override
				public Image getImage(Object element) {
					if (element instanceof AlAuthCheckStr) {
						if (isImageLabel(columnId)) {
							AlAuthCheckStr authCheck = (AlAuthCheckStr) element;
							String value = "";
							switch (columnId) {
							case AlAuthCheckStr.FLAG:
								value = authCheck.flag;
								break;
							case AlAuthCheckStr.FLAG01:
								value = authCheck.flag01;
								break;
							case AlAuthCheckStr.FLAG02:
								value = authCheck.flag02;
								break;
							case AlAuthCheckStr.FLAG03:
								value = authCheck.flag03;
								break;
							case AlAuthCheckStr.FLAG04:
								value = authCheck.flag04;
								break;
							case AlAuthCheckStr.FLAG05:
								value = authCheck.flag05;
								break;
							case AlAuthCheckStr.FLAG06:
								value = authCheck.flag06;
								break;
							case AlAuthCheckStr.FLAG07:
								value = authCheck.flag07;
								break;
							case AlAuthCheckStr.FLAG08:
								value = authCheck.flag08;
								break;
							case AlAuthCheckStr.FLAG09:
								value = authCheck.flag09;
								break;
							case AlAuthCheckStr.FLAG10:
								value = authCheck.flag10;
								break;
							}
							switch (value) {
							case WARNING: // $NON-NLS-1$ " Yellow Triangles
								return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.LED_YELLOW);
							// workbench.getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
							case ERROR: // $NON-NLS-1$
								return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.LED_RED);
							// workbench.getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
							default:
								// @5B@ green square
								return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.LED_GREEN);
							}
						} else
							return null;
					}
					return null;
				}

				private boolean isImageLabel(String columnId) {
					return (columnId.startsWith(AlAuthCheckStr.FLAGNN));
				}
			});
		}
	}

	private boolean selectedInPreferences(String columnId, ScopedPreferenceStore preferences) {
		String preferenceName;
		if (columnId.startsWith(AlAuthCheckStr.FIELDNN))
			preferenceName = AlAuthCheckStr.FIELDNN;
		else if (columnId.startsWith(AlAuthCheckStr.VALNN))
			preferenceName = AlAuthCheckStr.VALNN;
		else if (columnId.startsWith(AlAuthCheckStr.FLAGNN))
			preferenceName = AlAuthCheckStr.FLAGNN;
		else
			preferenceName = columnId;
		return preferences.getBoolean(AlAuthCheckStr.PREFIX + preferenceName);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				AuthView.this.fillContextMenu(manager, contextMenuActions);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	private void makeActions() {
		contextMenuActions = new ArrayList<>();
		ViewsManager.get().addViewsToContextMenu(viewer, contextMenuActions, ViewsManager.AUTH_CHECKS_ID);

		doubleClickAction = new Action() {
			@Override
			public void run() {
				try {
					IStructuredSelection selection = viewer.getStructuredSelection();
					Object obj = selection.getFirstElement();
					if (obj instanceof AlAuthCheckStr) {
						AlAuthCheckStr authCheck = (AlAuthCheckStr) obj;
						NavigationTarget target = new NavigationTarget(authCheck.alObject, authCheck.line);
						ViewsManager.navigateToObject(target);
					}
				} catch (JCoException e) {
					e.printStackTrace();
				}
			}

		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});

	}

	@PreDestroy
	public void setViewClosed() { // NO_UCD (unused code)
		ViewsManager.get().setViewClosed(ViewsManager.AUTH_CHECKS_ID);
	}

}
