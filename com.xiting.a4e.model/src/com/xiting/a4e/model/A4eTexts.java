package com.xiting.a4e.model;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class A4eTexts {
	private static final String BUNDLE_NAME = "com.xiting.a4e.model.a4eTexts"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private A4eTexts() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}
