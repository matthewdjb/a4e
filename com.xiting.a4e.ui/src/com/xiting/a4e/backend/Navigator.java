package com.xiting.a4e.backend;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.sap.adt.tools.core.ui.navigation.AdtNavigationServiceFactory;
import com.sap.adt.tools.core.ui.navigation.IAdtNavigationService;
import com.sap.conn.jco.JCoException;
import com.xiting.a4e.backend.bapi.uriGet.AlBapiUriGet;
import com.xiting.a4e.model.structures.AlGetUriParameters;
import com.xiting.a4e.model.structures.AlObjectStr;

public class Navigator implements INavigator {

	private static final String SOURCE_MAIN_START = "/source/main#start=";

	@Override
	public void jump_to(AlObjectStr target, int offset) throws JCoException {
		AlGetUriParameters parameters = new AlGetUriParameters(target);
		new AlBapiUriGet(parameters).run();
		if (target.type.equals("METH"))
			parameters.uri = convertMethodUriOffset(parameters.uri, offset);
		else
			parameters.uri = convertUriOffset(parameters.uri,offset);
		System.out.println(parameters.uri); // TODO
		IAdtNavigationService navServ = AdtNavigationServiceFactory.createNavigationService();
		IStatus status = navServ.navigateWithExternalLink(parameters.uri, null);
		if (!status.isOK())
			tryAlternateNavigation(navServ, parameters, target, offset);
		System.out.println(status.toString());
	}

	private String convertMethodUriOffset(String uri, int offset) {
		String[] components = uri.split(SOURCE_MAIN_START);
		String firstPart = String.valueOf(components[0]);
		String secondPart = String.valueOf(components[1]);
		components = secondPart.split(",");
		int newOffset = Integer.parseInt(components[0]) + offset - 2;
		return firstPart + SOURCE_MAIN_START + Integer.toString(newOffset) + ",0";
	}

	private String convertUriOffset(String uri, int offset) {
		return uri + SOURCE_MAIN_START + Integer.toString(offset) + ",0";
	}

	private void tryAlternateNavigation(IAdtNavigationService navServ, AlGetUriParameters parameters,
			AlObjectStr target, int offset) throws JCoException {
		if (target.type.equals("PROG") || target.type.equals("YEVI")) {
			parameters.subtype = "P";
			new AlBapiUriGet(parameters).run();
			parameters.uri = convertUriOffset(parameters.uri,offset);
			System.out.println("Alternative Uri:" + parameters.uri);
			IStatus status = navServ.navigateWithExternalLink(parameters.uri, null);
			if (!status.isOK())
				messageNotNavigable();
		}
		else
			messageNotNavigable();
	}

	private void messageNotNavigable() {
		MessageDialog.openWarning(Display.getDefault().getActiveShell(), "Warning",
				"This object cannot be navigated to");

	}

}
