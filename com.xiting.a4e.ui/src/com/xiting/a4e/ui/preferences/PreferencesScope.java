package com.xiting.a4e.ui.preferences;

import java.util.stream.IntStream;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlScopeStr;
import com.xiting.a4e.ui.A4eUiPlugin;
import com.xiting.a4e.ui.A4eUiTexts;

public class PreferencesScope extends PreferencePage implements IWorkbenchPreferencePage {
	private static final int CELLS_PER_ROW = 5;

	private static final int MAX_DEPTH = 32767;
	private ScanOption scanStandard;
	private ScanOption scanPartner;
	private ScanOption scanCustom;
	private CheckBox producerNameSpacesOnly;
	private Depth universalDepth;

	@Override
	protected Control createContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().numColumns(CELLS_PER_ROW).applyTo(comp);
		drawUniversalDepth(comp);
		addEmptyCells(comp, CELLS_PER_ROW);
		drawScanStandard(comp);
		drawScanProducer(comp);
		drawScanCustom(comp);
		return comp;
	}

	@Override
	public Image getImage() {
		return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.XITING_LOGO);
	}

	private void drawUniversalDepth(Composite comp) {
		addEmptyCell(comp); // 1
		Label depth = new Label(comp, SWT.NONE);
		depth.setText(A4eUiTexts.getString("PreferencesScope_UniversalDepth")); //$NON-NLS-1$
		universalDepth = new Depth(comp, AlScopeStr.UNIVERSAL_DEPTH);
		Button setAllButton = new Button(comp, SWT.PUSH);
		setAllButton.setToolTipText(A4eUiTexts.getString("PreferencesScope_SetAll")); //$NON-NLS-1$
		setAllButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_FORWARD));
		setAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDepths();
			}

		});
		addEmptyCell(comp);
	}

	private void drawScanStandard(Composite comp) {
		scanStandard = new ScanOption(comp, A4eUiTexts.getString("PreferencesScope_ScanStandardCode"), //$NON-NLS-1$
				AlScopeStr.STANDARD, AlScopeStr.STANDARD_DEPTH);
		addEmptyCells(comp, 2);
	}

	private void drawScanProducer(Composite comp) {
		scanPartner = new ScanOption(comp, A4eUiTexts.getString("PreferencesScope_ScanPartnerCode"), AlScopeStr.PARTNER, //$NON-NLS-1$
				AlScopeStr.PARTNER_DEPTH);
		producerNameSpacesOnly = new CheckBox(comp, A4eUiTexts.getString("PreferencesScope_ProducerNamespacesOnly"), //$NON-NLS-1$
				AlScopeStr.PARTNER_PRODUCER);
	}

	private void drawScanCustom(Composite comp) {
		scanCustom = new ScanOption(comp, A4eUiTexts.getString("PreferencesScope_ScanCustomCode"), AlScopeStr.CUSTOM, //$NON-NLS-1$
				AlScopeStr.CUSTOM_DEPTH);
		addEmptyCells(comp, 2);
	}

	private void addEmptyCell(Composite comp) {
		Label emptyCell = new Label(comp, SWT.NONE);
		emptyCell.setText(" "); //$NON-NLS-1$
		GridDataFactory.swtDefaults().applyTo(emptyCell);
	}

	private void addEmptyCells(Composite comp, int cells) {
		IntStream.range(0, cells).forEach(i -> {
			addEmptyCell(comp);
		});

	}

	private void setDepths() {
		String newDepth = universalDepth.scanDepth.getText();
		if (scanStandard.checkBox.scanCheckbox.getSelection())
			scanStandard.depth.scanDepth.setText(newDepth);
		if (scanCustom.checkBox.scanCheckbox.getSelection())
			scanCustom.depth.scanDepth.setText(newDepth);
		if (scanPartner.checkBox.scanCheckbox.getSelection())
			scanPartner.depth.scanDepth.setText(newDepth);
	}

	private class ScanOption {
		private CheckBox checkBox;
		private Depth depth;

		private ScanOption(Composite comp, String textForCheckBox, String checkboxField, String depthField) {
			checkBox = new CheckBox(comp, textForCheckBox, checkboxField);
			depth = new Depth(comp, depthField);
		}
	}

	private class CheckBox {
		private Label scanLabel;
		private Button scanCheckbox;

		private CheckBox(Composite comp, String textForCheckBox, String checkboxField) {
			scanCheckbox = new Button(comp, SWT.CHECK);
			scanCheckbox.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (e.widget instanceof Button) {
						toggleEnabled((Button) e.widget);
					}
				}

			});
			GridDataFactory.swtDefaults().applyTo(scanCheckbox);
			scanCheckbox.setSelection(getPreferenceStore().getBoolean(checkboxField));
			scanLabel = new Label(comp, SWT.NONE);
			scanLabel.setText(textForCheckBox);
			GridDataFactory.swtDefaults().applyTo(scanLabel);
		}

	}

	protected void toggleEnabled(Button button) {
		if (button == scanStandard.checkBox.scanCheckbox)
			toggleStandardEnabled(button.getSelection());
		if (button == scanPartner.checkBox.scanCheckbox)
			togglePartnerEnabled(button.getSelection());
		if (button == scanCustom.checkBox.scanCheckbox)
			toggleCustomEnabled(button.getSelection());

	}

	protected void toggleStandardEnabled(boolean newStatus) {
		scanStandard.depth.scanDepth.setEnabled(newStatus);
	}

	protected void toggleCustomEnabled(boolean newStatus) {
		scanCustom.depth.scanDepth.setEnabled(newStatus);
	}

	private void togglePartnerEnabled(boolean newStatus) {
		scanPartner.depth.scanDepth.setEnabled(newStatus);
		producerNameSpacesOnly.scanCheckbox.setEnabled(newStatus);
	}

	private class Depth {
		private Text scanDepth;

		private Depth(Composite comp, String depthField) {
			scanDepth = new Text(comp, SWT.BORDER);
			VerifyListener verifyIsNumericListener = new VerifyListener() {
				@Override
				public void verifyText(VerifyEvent e) {
					Text text = (Text) e.getSource();
					final String currentText = text.getText();
					String depth = currentText.substring(0, e.start) + e.text + currentText.substring(e.end);
					try {
						int depthNum = Integer.valueOf(depth);
						if (depthNum < 0 || depthNum > MAX_DEPTH) {
							e.doit = false;
						}
					} catch (NumberFormatException ex) {
						if (!depth.equals("")) //$NON-NLS-1$
							e.doit = false;
					}
				}
			};
			scanDepth.addVerifyListener(verifyIsNumericListener);
			scanDepth.setText(getPreferenceStore().getString(depthField));
		}
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(AlchemistController.factory().getPreferenceStore());
	}

	@Override
	protected void performDefaults() {
		scanCustom.checkBox.scanCheckbox.setSelection(getPreferenceStore().getDefaultBoolean(AlScopeStr.CUSTOM));
		scanCustom.depth.scanDepth.setText(getPreferenceStore().getDefaultString(AlScopeStr.CUSTOM_DEPTH));
		scanPartner.checkBox.scanCheckbox.setSelection(getPreferenceStore().getDefaultBoolean(AlScopeStr.PARTNER));
		scanPartner.depth.scanDepth.setText(getPreferenceStore().getDefaultString(AlScopeStr.PARTNER_DEPTH));
		producerNameSpacesOnly.scanCheckbox
				.setSelection(getPreferenceStore().getDefaultBoolean(AlScopeStr.PARTNER_PRODUCER));
		scanStandard.checkBox.scanCheckbox.setSelection(getPreferenceStore().getDefaultBoolean(AlScopeStr.STANDARD));
		scanStandard.depth.scanDepth.setText(getPreferenceStore().getDefaultString(AlScopeStr.STANDARD_DEPTH));
		universalDepth.scanDepth.setText(getPreferenceStore().getDefaultString(AlScopeStr.UNIVERSAL_DEPTH));
	}

	@Override
	public boolean okToLeave() {
		if (scanStandard.checkBox.scanCheckbox.getSelection() || scanCustom.checkBox.scanCheckbox.getSelection()
				|| scanPartner.checkBox.scanCheckbox.getSelection())
			return true;
		setErrorMessage(A4eUiTexts.getString("PreferencesScope_AtLeastOneScanMustBeSelected")); //$NON-NLS-1$
		return false;
	}

	@Override
	public boolean performOk() {
		if (okToLeave()) {
			getPreferenceStore().setValue(AlScopeStr.CUSTOM, scanCustom.checkBox.scanCheckbox.getSelection());
			getPreferenceStore().setValue(AlScopeStr.CUSTOM_DEPTH, scanCustom.depth.scanDepth.getText());
			getPreferenceStore().setValue(AlScopeStr.PARTNER, scanPartner.checkBox.scanCheckbox.getSelection());
			getPreferenceStore().setValue(AlScopeStr.PARTNER_DEPTH, scanPartner.depth.scanDepth.getText());
			getPreferenceStore().setValue(AlScopeStr.PARTNER_PRODUCER,
					producerNameSpacesOnly.scanCheckbox.getSelection());
			getPreferenceStore().setValue(AlScopeStr.STANDARD, scanStandard.checkBox.scanCheckbox.getSelection());
			getPreferenceStore().setValue(AlScopeStr.STANDARD_DEPTH, scanStandard.depth.scanDepth.getText());
			getPreferenceStore().setValue(AlScopeStr.UNIVERSAL_DEPTH, universalDepth.scanDepth.getText());
			return super.performOk();
		}
		return false;
	}
}
