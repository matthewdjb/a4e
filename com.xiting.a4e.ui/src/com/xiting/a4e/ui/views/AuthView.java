package com.xiting.a4e.ui.views;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;

import com.xiting.a4e.backend.bapi.alViews.BapiViewsFactory;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlAuthCheckStr;
import com.xiting.a4e.model.structures.AlFindingStr;
import com.xiting.a4e.model.structures.BapiViewsBean;
import com.xiting.a4e.ui.A4eUiTexts;

public class AuthView implements IAlchemistView {

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
		AlchemistController controller = AlchemistController.factory();
		BapiViewsBean viewsBean = controller.getViewsBean();
		if (viewsBean.authChecks == null) {
			viewsBean.authCheckFlag = true;
			BapiViewsFactory.getRunner().run(controller.getDestination());
		} else {
			viewer.setInput(viewsBean);
		}
	}

	@PostConstruct
	public void createPartControl(Composite parent) { // NO_UCD (unused code)
		ArrayList<AlAuthCheckStr> authBasic = AlchemistController.factory().getViewsBean().authChecks;
		if (authBasic == null)
			displayMessageInView(parent, A4eUiTexts.getString("NoAnalysis")); //$NON-NLS-1$
		else if (authBasic.isEmpty())
			displayMessageInView(parent, A4eUiTexts.getString("NoResults")); //$NON-NLS-1$
		else {
			displayAuthBasic(parent);
		}
		ViewsManager.get().setViewOpened(ViewsManager.BASIC_ID);
	}

	private void displayAuthBasic(Composite parent) {
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

	private void displayMessageInView(Composite parent, String message) {
		new Label(parent, SWT.BORDER).setText(message);
	}

	private void hookDoubleClickAction() {
		// TODO Auto-generated method stub

	}

	private void hookContextMenu() {
		// TODO Auto-generated method stub

	}

	private void makeActions() {
		// TODO Auto-generated method stub

	}
}
