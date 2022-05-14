package com.xiting.a4e.ui.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.xiting.a4e.model.structures.AlScopeStr;

public class AbstractLaunchConfigurationScopeTab extends AbstractLaunchConfigurationTab {

	private static final int MAX_DEPTH = 32767;
	private ScanOption scanStandard;
	private ScanOption scanPartner;
	private ScanOption scanCustom;
	private CheckBox producerNameSpacesOnly;
	private Depth universalDepth;

	@Override
	public void createControl(Composite parent) {
		Composite comp = new Group(parent, SWT.BORDER);
		setControl(comp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		universalDepth = new Depth(comp, "Universal depth");
		addEmptyLine(comp);
		scanStandard = new ScanOption(comp, "Scan standard code");
		addEmptyLine(comp);
		scanPartner = new ScanOption(comp, "Scan partner code");
		producerNameSpacesOnly = new CheckBox(comp, "Producer namespaces only");
		addEmptyLine(comp);
		scanCustom = new ScanOption(comp, "Scan custom code");
	}

	private void addEmptyLine(Composite comp) {
		Label emptyCell1 = new Label(comp, SWT.NONE);
		emptyCell1.setText("");
		GridDataFactory.swtDefaults().applyTo(emptyCell1);
		Label emptyCell2 = new Label(comp, SWT.NONE);
		emptyCell1.setText("");
		GridDataFactory.swtDefaults().applyTo(emptyCell2);
	}

	class ScanOption {
		public CheckBox checkBox;
		public Depth depth;

		public ScanOption(Composite comp, String textForCheckBox) {
			checkBox = new CheckBox(comp, textForCheckBox);
			depth = new Depth(comp);
		}
	}

	class CheckBox {
		public Label scanLabel;
		public Button scanCheckbox;
		public Boolean checkBoxValue;

		public CheckBox(Composite comp, String textForCheckBox) {
			scanLabel = new Label(comp, SWT.NONE);
			scanLabel.setText(textForCheckBox);
			GridDataFactory.swtDefaults().applyTo(scanLabel);
			scanCheckbox = new Button(comp, SWT.CHECK);
			GridDataFactory.swtDefaults().applyTo(scanCheckbox);
			scanCheckbox.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					Button btn = (Button) event.getSource();
					checkBoxValue = btn.getSelection();
					setDirty(true);
				}
			});
		}
	}

	class Depth {
		public Label scanDepthLabel;
		public Text scanDepth;

		public Depth(Composite comp) {
			this(comp, "Depth");
		}

		public Depth(Composite comp, String labalText) {
			scanDepthLabel = new Label(comp, SWT.NONE);
			scanDepthLabel.setText(labalText);
			scanDepth = new Text(comp, SWT.NONE);
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
						if (!depth.equals(""))
							e.doit = false;
					}
					if (e.doit)
						setDirty(true);
				}
			};
			scanDepth.addVerifyListener(verifyIsNumericListener);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(AlScopeStr.UNIVERSAL_DEPTH, "3");
		configuration.setAttribute(AlScopeStr.STANDARD, true);
		configuration.setAttribute(AlScopeStr.STANDARD_DEPTH, "3");
		configuration.setAttribute(AlScopeStr.PARTNER, true);
		configuration.setAttribute(AlScopeStr.PARTNER_DEPTH, "3");
		configuration.setAttribute(AlScopeStr.PARTNER_PRODUCER, false);
		configuration.setAttribute(AlScopeStr.CUSTOM, "3");
		configuration.setAttribute(AlScopeStr.CUSTOM_DEPTH, false);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			universalDepth.scanDepth.setText(configuration.getAttribute(AlScopeStr.UNIVERSAL_DEPTH, "3"));

			scanStandard.checkBox.checkBoxValue = (configuration.getAttribute(AlScopeStr.STANDARD, true));
			scanStandard.checkBox.scanCheckbox.setSelection(scanStandard.checkBox.checkBoxValue);
			scanStandard.depth.scanDepth.setText(configuration.getAttribute(AlScopeStr.STANDARD_DEPTH, "3"));

			scanPartner.checkBox.checkBoxValue = (configuration.getAttribute(AlScopeStr.PARTNER, true));
			scanPartner.checkBox.scanCheckbox.setSelection(scanPartner.checkBox.checkBoxValue);
			scanPartner.depth.scanDepth.setText(configuration.getAttribute(AlScopeStr.PARTNER_DEPTH, "3"));

			producerNameSpacesOnly.checkBoxValue = (configuration.getAttribute(AlScopeStr.PARTNER_PRODUCER, true));
			producerNameSpacesOnly.scanCheckbox.setSelection(producerNameSpacesOnly.checkBoxValue);

			scanCustom.checkBox.checkBoxValue = (configuration.getAttribute(AlScopeStr.CUSTOM, true));
			scanCustom.checkBox.scanCheckbox.setSelection(scanCustom.checkBox.checkBoxValue);
			scanCustom.depth.scanDepth.setText(configuration.getAttribute(AlScopeStr.CUSTOM_DEPTH, "3"));
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(AlScopeStr.UNIVERSAL_DEPTH, universalDepth.scanDepth.getText());
		configuration.setAttribute(AlScopeStr.STANDARD, scanStandard.checkBox.checkBoxValue);
		configuration.setAttribute(AlScopeStr.STANDARD_DEPTH, scanStandard.depth.scanDepth.getText());
		configuration.setAttribute(AlScopeStr.PARTNER, scanPartner.checkBox.checkBoxValue);
		configuration.setAttribute(AlScopeStr.PARTNER_DEPTH, scanPartner.depth.scanDepth.getText());
		configuration.setAttribute(AlScopeStr.PARTNER_PRODUCER, producerNameSpacesOnly.checkBoxValue);
		configuration.setAttribute(AlScopeStr.CUSTOM, scanCustom.checkBox.checkBoxValue);
		configuration.setAttribute(AlScopeStr.CUSTOM_DEPTH, scanCustom.depth.scanDepth.getText());
	}

	@Override
	public String getName() {
		return "Scope";
	}

}
