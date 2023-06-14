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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.sap.conn.jco.JCoException;
import com.xiting.a4e.backend.bapi.alViews.BapiViewsFactory;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlMissingAuthStr;
import com.xiting.a4e.model.structures.BapiViewsBean;
import com.xiting.a4e.model.structures.NavigationTarget;
import com.xiting.a4e.ui.A4eUiPlugin;
import com.xiting.a4e.ui.A4eUiTexts;

public class MissingAuthsView extends View implements IAlchemistView {

	private static final String GREEN_TICK = "@0V@";
	private static final String ALARM = "@1V@";

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

	MissingAuthsView() {
		AlchemistController controller = AlchemistController.factory();
		BapiViewsBean viewsBean = controller.getViewsBean();
		if (viewsBean.missingAuths == null) {
			viewsBean.missingAuthsFlag = true;
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
		viewer.setInput(viewsBean.missingAuths);
	}

	@PostConstruct
	public void createPartControl(Composite parent) { // NO_UCD (unused code)
		// Initialize the JCo environment
		ArrayList<AlMissingAuthStr> missingAuths = AlchemistController.factory().getViewsBean().missingAuths;
		if (missingAuths == null)
			displayMessageInView(parent, A4eUiTexts.getString("NoAnalysis")); //$NON-NLS-1$
		else if (missingAuths.isEmpty())
			displayMessageInView(parent, A4eUiTexts.getString("NoResults")); //$NON-NLS-1$
		else {
			displayMissingAuths(parent);
		}
		ViewsManager.get().setViewOpened(ViewsManager.MISSING_AUTH_CHECKS);
	}

	private void displayMissingAuths(Composite parent) {
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
		createColumn(AlMissingAuthStr.SU24_TYPE);
		createColumn(AlMissingAuthStr.SU24_NAME);
		createColumn(AlMissingAuthStr.SU24_OBJECT);
		createColumn(AlMissingAuthStr.SU24_FIELD);
		createColumn(AlMissingAuthStr.SU24_LOW);
		createColumn(AlMissingAuthStr.OBJECT);
		createColumn(AlMissingAuthStr.FIELD);
		createColumn(AlMissingAuthStr.VALUE);
		createColumn(AlMissingAuthStr.SU24_NOCHECK);
		createColumn(AlMissingAuthStr.DEPTH);
		createColumn(AlMissingAuthStr.LINE);
		createColumn(AlMissingAuthStr.SCOPETYPE);
		createColumn(AlMissingAuthStr.TYPE);
		createColumn(AlMissingAuthStr.INCLUDE);
	}

	private void createColumn(String columnId) {
		String headingText = AlMissingAuthStr.getColumnDescription(columnId);
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
					if (element instanceof AlMissingAuthStr) {
						if (isImageLabel(columnId))
							return null;
						else {
							Map<String, String> authCheckSu24 = ((AlMissingAuthStr) element).returnAsMapString();
							colText = authCheckSu24.get(columnId);
						}
					}
					return colText;
				}

				@Override
				public Image getImage(Object element) {
					if (element instanceof AlMissingAuthStr) {
						if (isImageLabel(columnId)) {
							AlMissingAuthStr authCheckSu24 = (AlMissingAuthStr) element;
							String value = "";
							switch (columnId) {
							case AlMissingAuthStr.SU24_OBJECT:
								value = authCheckSu24.su24Object;
								break;
							case AlMissingAuthStr.SU24_FIELD:
								value = authCheckSu24.su24Field;
								break;
							case AlMissingAuthStr.SU24_LOW:
								value = authCheckSu24.su24Low;
								break;
							default:
								return null;
							}
							if (!(value == null))
								switch (value) {
								case GREEN_TICK:
									return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.GREEN_TICK);
								case ALARM:
									return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.ALARM);
								}
						} else
							return null;
					}
					return null;
				}

				private boolean isImageLabel(String columnId) {
					switch (columnId) {
					case AlMissingAuthStr.SU24_OBJECT:
					case AlMissingAuthStr.SU24_FIELD:
					case AlMissingAuthStr.SU24_LOW:
						return true;
					default:
						return false;
					}
				}
			});
		}
	}

	private boolean selectedInPreferences(String columnId, ScopedPreferenceStore preferences) {
		return preferences.getBoolean(AlMissingAuthStr.PREFIX + columnId);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				MissingAuthsView.this.fillContextMenu(manager, contextMenuActions);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	private void makeActions() {
		contextMenuActions = new ArrayList<>();
		ViewsManager.get().addViewsToContextMenu(viewer, contextMenuActions, ViewsManager.MISSING_AUTH_CHECKS);

		doubleClickAction = new Action() {
			@Override
			public void run() {
				try {
					IStructuredSelection selection = viewer.getStructuredSelection();
					Object obj = selection.getFirstElement();
					if (obj instanceof AlMissingAuthStr) {
						AlMissingAuthStr authCheck = (AlMissingAuthStr) obj;
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
		ViewsManager.get().setViewClosed(ViewsManager.MISSING_AUTH_CHECKS);
	}

}
