package com.xiting.a4e.ui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.sap.conn.jco.JCoException;
import com.xiting.a4e.backend.INavigator;
import com.xiting.a4e.backend.NavigatorFactory;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.NavigationTarget;
import com.xiting.a4e.ui.A4eUiTexts;

public final class ViewsManager {

	public static final String BASIC_ID = "com.xiting.a4e.ui.basic"; //$NON-NLS-1$
	public static final String CALL_STACK_ID = "com.xiting.a4e.ui.callStack"; //$NON-NLS-1$
	public static final String AUTH_CHECKS_ID = "com.xiting.a4e.ui.authBasic"; //$NON-NLS-1$
	public static final String AUTH_CHECKS_SU24_ID = "com.xiting.a4e.ui.authSu24"; //$NON-NLS-1$

	private static ViewsManager singleton = new ViewsManager();
	private Map<String, ViewProperties> viewIds = new HashMap<>();
	private Map<String, Boolean> openViews = new HashMap<>();

	private class ViewProperties {
		boolean defaultValue;
		String preferenceDescription;
		String menuDescription;

		ViewProperties(boolean defaultValue, String preferenceDescription, String menuDescription) {
			this.defaultValue = defaultValue;
			this.preferenceDescription = preferenceDescription;
			this.menuDescription = menuDescription;
		}

		ViewProperties(String preferenceDescription, String menuDescription) {
			this(false, preferenceDescription, menuDescription);
		}
	}

	public static final ViewsManager get() {
		return singleton;
	}

	private ViewsManager() {
		addViewId(BASIC_ID, new ViewProperties(true, A4eUiTexts.getString("PreferencesViews.Basic"), //$NON-NLS-1$
				A4eUiTexts.getString("BasicView.OpenBasicView"))); //$NON-NLS-1$
		addViewId(CALL_STACK_ID, new ViewProperties(A4eUiTexts.getString("PreferencesViews.CallStack"), //$NON-NLS-1$
				A4eUiTexts.getString("BasicView.OpenCallStackView"))); //$NON-NLS-1$
		addViewId(AUTH_CHECKS_ID ,new ViewProperties(A4eUiTexts.getString("PreferencesViews.AuthChecks"), //$NON-NLS-1$
				A4eUiTexts.getString("BasicView.OpenAuthChecksView"))); //$NON-NLS-1$
		addViewId(AUTH_CHECKS_SU24_ID ,new ViewProperties(A4eUiTexts.getString("PreferencesViews.AuthChecksSu24"), //$NON-NLS-1$
				A4eUiTexts.getString("BasicView.OpenAuthChecksSu24View"))); //$NON-NLS-1$
	}

	private final void addViewId(String viewId, ViewProperties properties) {
		if (viewIds.get(viewId) == null) {
			viewIds.put(viewId, properties);
		}
	}

	private final IViewPart openView(String viewId) throws PartInitException {
		IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(viewId);
		if (view != null)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewId);
		return view;
	}

	private final void updateView(IViewPart iViewPart) {
		if (iViewPart instanceof IAlchemistView)
			((IAlchemistView) iViewPart).refresh();
	}

	public void updateViews() throws PartInitException {
		for (Entry<String, ViewProperties> viewEntry : viewIds.entrySet()) {
			ScopedPreferenceStore preferences = AlchemistController.factory().getPreferenceStore();
			String viewId = viewEntry.getKey();
			if (preferences.getBoolean(viewId) || (openViews.get(viewId) != null && openViews.get(viewId)))
				updateView(openView(viewEntry.getKey()));
		}

	}

	private void updateView(String viewId) throws PartInitException {
		updateView(openView(viewId));
	}

	public void setDefaults() {
		ScopedPreferenceStore preferences = AlchemistController.factory().getPreferenceStore();
		for (Entry<String, ViewProperties> viewEntry : viewIds.entrySet()) {
			preferences.setDefault(viewEntry.getKey(), viewEntry.getValue().defaultValue);
		}
	}

	public String getDescription(String id) {
		return viewIds.get(id).preferenceDescription;
	}

	private ArrayList<String> getAllViewIdsExcepting(String exceptThisId) {
		ArrayList<String> allViewsExcept = new ArrayList<>(viewIds.size());
		for (Entry<String, ViewProperties> viewEntry : viewIds.entrySet()) {
			if (viewEntry.getKey() != exceptThisId)
				allViewsExcept.add(viewEntry.getKey());
		}
		return allViewsExcept;
	}

	private String getMenuText(String viewID) {
		return viewIds.get(viewID).menuDescription;
	}

	void addViewsToContextMenu(ColumnViewer viewer, ArrayList<Action> contextMenuActions,
			String exceptForThisViewId) {
		for (String viewID : ViewsManager.get().getAllViewIdsExcepting(exceptForThisViewId)) {
			Action contextMenuAction = new Action(ViewsManager.get().getMenuText(viewID)) {
				@Override
				public void run() {
					try {
						if (viewer instanceof ColumnViewer) {
							ViewsManager.get().updateView(viewID);
						}
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}

			};
			contextMenuActions.add(contextMenuAction);
		}
	}

	static void navigateToObject(NavigationTarget target) throws JCoException {
		INavigator navigator = NavigatorFactory.get();
		navigator.jump_to(target);
	}

	public void setViewOpened(String viewId) {
		if (openViews.get(viewId) == null)
			openViews.put(viewId, true);
		else
			openViews.replace(viewId, true);
	}

	public void setViewClosed(String viewId) {
		if (openViews.get(viewId) == null)
			openViews.put(viewId, false);
		else
			openViews.replace(viewId, false);
	}

}
