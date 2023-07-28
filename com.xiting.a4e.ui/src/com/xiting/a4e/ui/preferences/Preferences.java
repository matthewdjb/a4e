package com.xiting.a4e.ui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.xiting.a4e.ui.A4eUiPlugin;
import com.xiting.a4e.ui.A4eUiTexts;

public class Preferences extends PreferencePage implements IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		noDefaultAndApplyButton();
	}

	@Override
	protected Control createContents(Composite parent) {
		final var composite = new Composite(parent, SWT.NONE);
		final var layout = new GridLayout(1, false);
		layout.marginHeight = layout.marginWidth = 0;
		composite.setLayout(layout);
		Text text = new Text(composite, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
		text.setText(A4eUiTexts.getString("Preferences_SeeSubPages")); //$NON-NLS-1$
		Color foregroundColour = new Color(205, 19, 22);
		text.setForeground(foregroundColour);
		Color backgroundColour = new Color(255, 255, 255);
		if (Display.isSystemDarkTheme()) {
			text.setBackground(backgroundColour);
		}
		text.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				foregroundColour.dispose();
				backgroundColour.dispose();
			}
		});
		return composite;
	}
	
	@Override
	public Image getImage() {
		return A4eUiPlugin.getDefault().getImage(A4eUiPlugin.XITING_LOGO);
	}
}
