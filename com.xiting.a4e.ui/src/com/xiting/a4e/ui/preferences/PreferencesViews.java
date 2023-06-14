package com.xiting.a4e.ui.preferences;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.ui.A4eUiTexts;
import com.xiting.a4e.ui.views.ViewsManager;

public class PreferencesViews extends PreferencePage implements IWorkbenchPreferencePage {
	private ViewCheckbox basicView;
	private ViewCheckbox callstackView;

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(AlchemistController.factory().getPreferenceStore());
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		basicView = new ViewCheckbox(comp, ViewsManager.BASIC_ID);
		callstackView = new ViewCheckbox(comp, ViewsManager.CALL_STACK_ID);
		return comp;
	}

	private class ViewCheckbox {
		private Label label;
		private Button checkbox;

		public ViewCheckbox(Composite comp, String viewId) {
			drawObjects(comp, viewId);
			checkbox.setSelection(getPreferenceStore().getBoolean(viewId));
		}

		private void drawObjects(Composite comp, String viewId) {
			label = new Label(comp, SWT.NONE);
			label.setText(ViewsManager.get().getDescription(viewId));
			GridDataFactory.swtDefaults().applyTo(label);
			checkbox = new Button(comp, SWT.CHECK);
			GridDataFactory.swtDefaults().applyTo(checkbox);
		}
	}

	@Override
	protected void performDefaults() {
		basicView.checkbox.setSelection(getPreferenceStore().getDefaultBoolean(ViewsManager.BASIC_ID));
		callstackView.checkbox.setSelection(getPreferenceStore().getDefaultBoolean(ViewsManager.CALL_STACK_ID));
		setErrorMessage(null);
		super.performDefaults();
	}

	@Override
	public boolean okToLeave() {
		if (basicView.checkbox.getSelection() || callstackView.checkbox.getSelection())
			return true;
		setErrorMessage(A4eUiTexts.getString("PreferencesViews.AtLeastOneViewMustBeSelected")); //$NON-NLS-1$
		return false;
	}

	@Override
	public boolean performOk() {
		if (okToLeave()) {
			getPreferenceStore().setValue(ViewsManager.BASIC_ID, basicView.checkbox.getSelection());
			getPreferenceStore().setValue(ViewsManager.CALL_STACK_ID, callstackView.checkbox.getSelection());
			setErrorMessage(null);
			return super.performOk();
		}
		return false;
	}
}
