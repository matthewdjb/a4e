package com.xiting.a4e.ui.preferences;

import java.util.ArrayList;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlAuthCheckSu24Str;
import com.xiting.a4e.ui.A4eUiPlugin;

public class PreferencesAuthSu24Check extends PreferencePage implements IWorkbenchPreferencePage {
	private ArrayList<ColumnCheckbox> columnCheckboxes = new ArrayList<>();

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(AlchemistController.factory().getPreferenceStore());
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.SU24_TYPE));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.SU24_NAME));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.OBJECT));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.FIELD));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.VALUE));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.SU24_OBJECT));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.SU24_FIELD));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.SU24_LOW));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.SU24_NOCHECK));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.DEPTH));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.LINE));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.SCOPETYPE));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.TYPE));
		columnCheckboxes.add(new ColumnCheckbox(comp, AlAuthCheckSu24Str.NAME));
		return comp;
	}

	@Override
	public Image getImage() {
		return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.XITING_LOGO);
	}

	private class ColumnCheckbox {
		private Label label;
		private Button checkbox;
		private String preferenceName;

		private ColumnCheckbox(Composite comp, String preferenceName) {
			String description = AlAuthCheckSu24Str.getColumnDescription(preferenceName);
			drawObjects(comp, description);
			this.preferenceName = AlAuthCheckSu24Str.PREFIX + preferenceName;
			checkbox.setSelection(getPreferenceStore().getBoolean(this.preferenceName));
		}

		private void drawObjects(Composite comp, String description) {
			label = new Label(comp, SWT.NONE);
			label.setText(description);
			GridDataFactory.swtDefaults().applyTo(label);
			checkbox = new Button(comp, SWT.CHECK);
			GridDataFactory.swtDefaults().applyTo(checkbox);
		}
	}

	@Override
	protected void performDefaults() {
		for (ColumnCheckbox columnCheckbox : columnCheckboxes) {
			columnCheckbox.checkbox.setSelection(
					getPreferenceStore().getDefaultBoolean(AlAuthCheckSu24Str.PREFIX + columnCheckbox.preferenceName));
		}
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		for (ColumnCheckbox columnCheckbox : columnCheckboxes) {
			getPreferenceStore().setValue(AlAuthCheckSu24Str.PREFIX + columnCheckbox.preferenceName,
					columnCheckbox.checkbox.getSelection());
		}
		return super.performOk();
	}
}
