package com.xiting.a4e.ui;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class A4eUiPlugin extends AbstractUIPlugin {
	public static final String EVENT = "ICON_EVENT"; //$NON-NLS-1$
	private static final String EVENT_IMPLEMENTATION = "ICON_EVENT_IMPLEMENTATION"; //$NON-NLS-1$
	private static final String FUNCTION = "ICON_FUNCTION"; //$NON-NLS-1$
	private static final String CLASS = "ICON_CLASS"; //$NON-NLS-1$
	private static final String METHOD = "ICON_METHOD"; //$NON-NLS-1$
	private static final String PERFORM = "ICON_PERFORM"; //$NON-NLS-1$
	private static final String PATTERN = "ICON_PATTERN"; //$NON-NLS-1$
	private static final String FINDING = "ICON_FINDING"; //$NON-NLS-1$
	private static final String OTHER = "ICON_OTHER"; //$NON-NLS-1$
	private static final String SCREEN = "ICON_SCREEN"; //$NON-NLS-1$
	private static final String DYNPRO_MODULE = "ICON_DYNPRO_MODULE"; //$NON-NLS-1$
	public static final String XITING_LOGO = "ICON_XITING_LOGO"; //$NON-NLS-1$
	public static final String LED_GREEN = "LED_GREEN"; //$NON-NLS-1$
	public static final String LED_YELLOW = "LED_YELLOW"; //$NON-NLS-1$
	public static final String LED_RED = "LED_RED"; //$NON-NLS-1$
	public static final String GREEN_TICK = "GREEN_TICK"; //$NON-NLS-1$
	public static final String ALARM = "ALARM"; //$NON-NLS-1$

	private static final String FILE_EVENT = "$NL$/icons/sapEvent.gif"; //$NON-NLS-1$
	private static final String FILE_EVENT_IMPLEMENTATION = "$NL$/icons/sapEventImplementation.gif"; //$NON-NLS-1$
	private static final String FILE_FUNCTION = "$NL$/icons/sapfunction.gif"; //$NON-NLS-1$
	private static final String FILE_CLASS = "$NL$/icons/sapOOClass.gif"; //$NON-NLS-1$
	private static final String FILE_METHOD = "$NL$/icons/sapOOmethod.gif"; //$NON-NLS-1$
	private static final String FILE_PERFORM = "$NL$/icons/sapPerform.gif"; //$NON-NLS-1$
	private static final String FILE_PATTERN = "$NL$/icons/sapVariants.gif"; //$NON-NLS-1$
	private static final String FILE_FINDING = "$NL$/icons/sapSearch.gif"; //$NON-NLS-1$
	private static final String FILE_OTHER = "$NL$/icons/sapVariants.gif"; //$NON-NLS-1$
	private static final String FILE_SCREEN = "$NL$/icons/sapColor.gif"; //$NON-NLS-1$
	private static final String FILE_DYNPRO_MODULE = "$NL$/icons/sapAbap.gif"; //$NON-NLS-1$
	private static final String FILE_XITING_LOGO = "$NL$/icons/xiting_logo_icon.png"; //$NON-NLS-1$
	private static final String FILE_LED_GREEN = "$NL$/icons/sapLedGreen.gif"; //$NON-NLS-1$
	private static final String FILE_LED_YELLOW = "$NL$/icons/sapLedYellow.gif"; //$NON-NLS-1$
	private static final String FILE_LED_RED = "$NL$/icons/sapLedRed.gif"; //$NON-NLS-1$
	private static final String FILE_GREEN_TICK = "$NL$/icons/sapGreenTick.gif"; //$NON-NLS-1$
	private static final String FILE_ALARM = "$NL$/icons/sapAlarm.gif"; //$NON-NLS-1$

	private static A4eUiPlugin plugin;

	public A4eUiPlugin() {
		plugin = this;
	}

	@Override
	protected void initializeImageRegistry(final ImageRegistry imageRegistry) {
		registerImage(imageRegistry, EVENT, FILE_EVENT);
		registerImage(imageRegistry, EVENT_IMPLEMENTATION, FILE_EVENT_IMPLEMENTATION);
		registerImage(imageRegistry, FUNCTION, FILE_FUNCTION);
		registerImage(imageRegistry, CLASS, FILE_CLASS);
		registerImage(imageRegistry, METHOD, FILE_METHOD);
		registerImage(imageRegistry, PERFORM, FILE_PERFORM);
		registerImage(imageRegistry, PATTERN, FILE_PATTERN);
		registerImage(imageRegistry, FINDING, FILE_FINDING);
		registerImage(imageRegistry, OTHER, FILE_OTHER);
		registerImage(imageRegistry, SCREEN, FILE_SCREEN);
		registerImage(imageRegistry, DYNPRO_MODULE, FILE_DYNPRO_MODULE);
		registerImage(imageRegistry, XITING_LOGO, FILE_XITING_LOGO);
		registerImage(imageRegistry, LED_GREEN, FILE_LED_GREEN);
		registerImage(imageRegistry, LED_YELLOW, FILE_LED_YELLOW);
		registerImage(imageRegistry, LED_RED, FILE_LED_RED);
		registerImage(imageRegistry, GREEN_TICK, FILE_GREEN_TICK);
		registerImage(imageRegistry, ALARM, FILE_ALARM);
	}

	public static A4eUiPlugin getDefault() {
		return plugin;
	}

	public static Image getIcon(String type) {
		Image icon;
		switch (type) {
		case "CLAS": //$NON-NLS-1$
			icon = plugin.getImage(CLASS);
			break;
		case "DYNP": //$NON-NLS-1$
			icon = plugin.getImage(DYNPRO_MODULE);
			break;
		case "METH": //$NON-NLS-1$
			icon = plugin.getImage(METHOD);
			break;
		case "FUNC": //$NON-NLS-1$
			icon = plugin.getImage(FUNCTION);
			break;
		case "YMOD": //$NON-NLS-1$
			icon = plugin.getImage(DYNPRO_MODULE);
			break;
		case "YEVT": //$NON-NLS-1$
			icon = plugin.getImage(EVENT);
			break;
		case "YEVI": //$NON-NLS-1$
			icon = plugin.getImage(EVENT_IMPLEMENTATION);
			break;
		case "YFRM": //$NON-NLS-1$
			icon = plugin.getImage(PERFORM);
			break;
		case "Message": //$NON-NLS-1$
			icon = plugin.getImage(FINDING);
			break;
		default:
			icon = plugin.getImage(OTHER);
		}
		return icon;
	}

	private void registerImage(final ImageRegistry registry, final String imageId, final String fileName) {
		final Bundle bundle = FrameworkUtil.getBundle(getClass());
		if (bundle == null) {
			return;
		}
		final IPath path = new Path(fileName);
		final URL url = FileLocator.find(bundle, path, null);
		if (url == null) {
			return;
		}
		final ImageDescriptor desc = ImageDescriptor.createFromURL(url);

		if (registry.get(imageId) != null) {
			throw new IllegalStateException("duplicate imageId in image registry."); //$NON-NLS-1$
		}
		registry.put(imageId, desc);
	}

	public Image getImage(final String key) {
		return getImageRegistry().get(key);
	}

}
