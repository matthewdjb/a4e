package com.xiting.a4e.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class A4eUiTexts {
	private static final String BUNDLE_NAME = "com.xiting.a4e.ui.a4eUiTexts"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private A4eUiTexts() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}

