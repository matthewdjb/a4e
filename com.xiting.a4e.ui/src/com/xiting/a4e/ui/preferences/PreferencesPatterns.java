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
import com.xiting.a4e.model.structures.AlPatternStr;
import com.xiting.a4e.ui.A4eUiPlugin;
import com.xiting.a4e.ui.A4eUiTexts;

public class PreferencesPatterns extends PreferencePage implements IWorkbenchPreferencePage {
	private ArrayList<PatternCheckbox> patternCheckboxes = new ArrayList<>();

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(AlchemistController.factory().getPreferenceStore());
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		ArrayList<AlPatternStr> patterns = AlchemistController.factory().getPatterns();
		for (AlPatternStr pattern : patterns) {
			patternCheckboxes.add(new PatternCheckbox(comp, pattern.descr, pattern.className));
		}
		return comp;
	}

	@Override
	public Image getImage() {
		return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.XITING_LOGO);
	}

	private class PatternCheckbox {
		private Label label;
		private Button checkbox;
		private String preferenceName;

		private PatternCheckbox(Composite comp, String description, String preferenceName) {
			label = new Label(comp, SWT.NONE);
			label.setText(description);
			GridDataFactory.swtDefaults().applyTo(label);
			checkbox = new Button(comp, SWT.CHECK);
			GridDataFactory.swtDefaults().applyTo(checkbox);
			checkbox.setSelection(getPreferenceStore().getBoolean(preferenceName));
			this.preferenceName = preferenceName;
		}
	}

	@Override
	protected void performDefaults() {
		for (PatternCheckbox patternCheckbox : patternCheckboxes) {
			patternCheckbox.checkbox
					.setSelection(getPreferenceStore().getDefaultBoolean(patternCheckbox.preferenceName));
		}
		setErrorMessage(null);
	}

	@Override
	public boolean okToLeave() {
		for (PatternCheckbox patternCheckbox : patternCheckboxes) {
			boolean checked = patternCheckbox.checkbox.getSelection();
			if (checked)
				return true;
		}
		setErrorMessage(A4eUiTexts.getString("PreferencesPatterns.AtLeastOnePatternMustBeSelected")); //$NON-NLS-1$
		return false;
	}

	@Override
	public boolean performOk() {
		if (okToLeave()) {
			for (PatternCheckbox patternCheckbox : patternCheckboxes) {
				boolean checked = patternCheckbox.checkbox.getSelection();
				getPreferenceStore().setValue(patternCheckbox.preferenceName, checked);
			}
			setErrorMessage(null);
			return super.performOk();
		}
		return false;
	}
}
