package com.xiting.a4e.backend;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.sap.adt.tools.core.ui.navigation.AdtNavigationServiceFactory;
import com.sap.adt.tools.core.ui.navigation.IAdtNavigationService;
import com.sap.conn.jco.JCoException;
import com.xiting.a4e.backend.bapi.uriGet.AlBapiUriGet;
import com.xiting.a4e.model.structures.AlGetUriParameters;
import com.xiting.a4e.model.structures.NavigationTarget;
import com.xiting.a4e.ui.A4eUiTexts;

class Navigator implements INavigator {

	private static final String SOURCE_MAIN_START = "/source/main#start="; //$NON-NLS-1$

	@Override
	public void jump_to(NavigationTarget target) throws JCoException {
		AlGetUriParameters parameters = new AlGetUriParameters(target.object);
		new AlBapiUriGet(parameters).run();
		if (target.object.type.equals("METH")) //$NON-NLS-1$
			parameters.uri = convertMethodUriOffset(parameters.uri, target.codepos);
		else
			parameters.uri = convertUriOffset(parameters.uri, target.codepos);
		IAdtNavigationService navServ = AdtNavigationServiceFactory.createNavigationService();
		IStatus status = navServ.navigateWithExternalLink(parameters.uri, null);
		if (!status.isOK())
			tryAlternateNavigation(navServ, parameters, target);
	}

	private String convertMethodUriOffset(String uri, int offset) {
		String[] components = uri.split(SOURCE_MAIN_START);
		String firstPart = String.valueOf(components[0]);
		String secondPart = String.valueOf(components[1]);
		components = secondPart.split(","); //$NON-NLS-1$
		int newOffset = Integer.parseInt(components[0]) + offset - 1;
		return firstPart + SOURCE_MAIN_START + Integer.toString(newOffset) + ",0"; //$NON-NLS-1$
	}

	private String convertUriOffset(String uri, int offset) {
		return uri + SOURCE_MAIN_START + Integer.toString(offset) + ",0"; //$NON-NLS-1$
	}

	private void tryAlternateNavigation(IAdtNavigationService navServ, AlGetUriParameters parameters,
			NavigationTarget target) throws JCoException {
		if (target.object.type.equals("PROG") || target.object.type.equals("YEVI") //$NON-NLS-1$ //$NON-NLS-2$
				|| target.object.type.equals("YEVT")    //$NON-NLS-1$ //$NON-NLS-2$
				|| target.object.type.equals("YMOD")) { //$NON-NLS-1$ //$NON-NLS-2$
			parameters.subtype = "P"; //$NON-NLS-1$
			parameters.name = target.object.name;
			new AlBapiUriGet(parameters).run();
			parameters.uri = convertUriOffset(parameters.uri, target.codepos);
			IStatus status = navServ.navigateWithExternalLink(parameters.uri, null);
			if (!status.isOK())
				messageNotNavigable();
		} else
			messageNotNavigable();
	}

	private void messageNotNavigable() {
		MessageDialog.openWarning(Display.getDefault().getActiveShell(), A4eUiTexts.getString("Navigator_Warning"), //$NON-NLS-1$
				A4eUiTexts.getString("Navigator_NoNavigation")); //$NON-NLS-1$

	}

}
