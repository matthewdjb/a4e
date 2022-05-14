package com.xiting.a4e.backend.bapi.uriGet;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.*;

public class AlBapiUriGet {
	private static final String ALCHEMIST_BADI = "/XITING/AL_BAPI_URI_GET";

	private AlGetUriParameters parameters;
	private JCoDestination destination;
	private JCoFunction function;

	public AlBapiUriGet(AlGetUriParameters importParameters) throws JCoException {
		this.parameters = importParameters;
		this.destination = AlchemistController.factory().getDestination();
		this.function = destination.getRepository().getFunction(ALCHEMIST_BADI);
	}

	public void run() {
		try {
			setImportParameters();
			function.execute(destination);
			readUriParameter();
			readReturnParameter();
		} catch (JCoException e) {
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "System Failure", e.getMessage());
		}
	}

	private void readUriParameter() {
		parameters.uri = function.getExportParameterList().getString(AlGetUriParameters.URI_PARAMETER);
	}

	private void readReturnParameter() {
		JCoStructure bapiret2 = function.getExportParameterList().getStructure(AlGetUriParameters.RETURN_PARAMETER);
		parameters.bapiReturn = new BapiRet2();
		parameters.bapiReturn.type = bapiret2.getString(BapiRet2.TYPE);
		parameters.bapiReturn.id = bapiret2.getString(BapiRet2.ID);
		parameters.bapiReturn.number = bapiret2.getString(BapiRet2.NUMBER);
		parameters.bapiReturn.message = bapiret2.getString(BapiRet2.MESSAGE);
		parameters.bapiReturn.logNo = bapiret2.getString(BapiRet2.LOG_NO);
		parameters.bapiReturn.logMsgNo = bapiret2.getString(BapiRet2.LOG_MSG_NO);
		parameters.bapiReturn.messageV1 = bapiret2.getString(BapiRet2.MESSAGE_V1);
		parameters.bapiReturn.messageV2 = bapiret2.getString(BapiRet2.MESSAGE_V2);
		parameters.bapiReturn.messageV3 = bapiret2.getString(BapiRet2.MESSAGE_V3);
		parameters.bapiReturn.messageV4 = bapiret2.getString(BapiRet2.MESSAGE_V4);
		parameters.bapiReturn.row = bapiret2.getInt(BapiRet2.ROW);
		parameters.bapiReturn.field = bapiret2.getString(BapiRet2.FIELD);
		parameters.bapiReturn.system = bapiret2.getString(BapiRet2.SYSTEM);
	}

	private void setImportParameters() {
		function.getImportParameterList().setValue(AlGetUriParameters.NAME_PARAMETER, parameters.name);
		function.getImportParameterList().setValue(AlGetUriParameters.OBJECT_TYPE_PARAMETER, parameters.object_type);
		function.getImportParameterList().setValue(AlGetUriParameters.SUBTYPE_PARAMETER, parameters.subtype);
	}

}