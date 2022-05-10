package com.xiting.a43.backend.bapi.inspRunAdhoc;


import java.util.ArrayList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.xiting.a4e.model.structures.BapiBean;
import com.xiting.a4e.model.structures.BapiRet2;
import com.xiting.a4e.model.structures.*;



public class AlBapiInspRunAdhocRunner {
	private static final String ALCHEMIST_BADI = "/XITING/AL_BAPI_INSP_RUN_ADHOC";
	public final static char ABAP_TRUE = 'X';
	public final static char ABAP_FALSE = ' ';

	private BapiBean bean;
	private JCoDestination destination;
	private JCoFunction function;
	public Thread thread;

	public AlBapiInspRunAdhocRunner(BapiBean bean, JCoDestination destination) throws JCoException {
		this.bean = bean;
		this.function = destination.getRepository().getFunction(ALCHEMIST_BADI);
		this.destination = destination;
	}

	public void run() {
		try {
			setScopeParameter(function, bean);
			setObjectsParameter(function, bean);
			setPatternsParameter(function, bean);
			function.execute(destination);
			readFindingsParameter(function, bean);
			readCallstackParameter(function, bean);
			readReturnParameter(function, bean);
			readRetcodeParameter(function, bean);
		} catch (JCoException e) {
//			if (e.getGroup() == JCoException.JCO_ERROR_SYSTEM_FAILURE)
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "System Failure", e.getMessage());
//			else
//				e.printStackTrace();
		}
	}

	private void readReturnParameter(JCoFunction function, BapiBean bean) {
		JCoTable bapiret2Table = function.getExportParameterList().getTable(BapiBean.RETURN_PARAMETER);
		bean.bapiReturn = new ArrayList<BapiRet2>();
		if (!bapiret2Table.isEmpty()) {
			do {
				BapiRet2 bapiret2 = new BapiRet2();
				bapiret2.type = bapiret2Table.getString(BapiRet2.TYPE);
				bapiret2.id = bapiret2Table.getString(BapiRet2.ID);
				bapiret2.number = bapiret2Table.getString(BapiRet2.NUMBER);
				bapiret2.message = bapiret2Table.getString(BapiRet2.MESSAGE);
				bapiret2.logNo = bapiret2Table.getString(BapiRet2.LOG_NO);
				bapiret2.logMsgNo = bapiret2Table.getString(BapiRet2.LOG_MSG_NO);
				bapiret2.messageV1 = bapiret2Table.getString(BapiRet2.MESSAGE_V1);
				bapiret2.messageV2 = bapiret2Table.getString(BapiRet2.MESSAGE_V2);
				bapiret2.messageV3 = bapiret2Table.getString(BapiRet2.MESSAGE_V3);
				bapiret2.messageV4 = bapiret2Table.getString(BapiRet2.MESSAGE_V4);
				bapiret2.row = bapiret2Table.getInt(BapiRet2.ROW);
				bapiret2.field = bapiret2Table.getString(BapiRet2.FIELD);
				bapiret2.system = bapiret2Table.getString(BapiRet2.SYSTEM);
				bean.bapiReturn.add(bapiret2);
			} while (bapiret2Table.nextRow());
		}
	}

	private void readRetcodeParameter(JCoFunction function, BapiBean bean) {
		bean.retcode = function.getExportParameterList().getInt(BapiBean.RETCODE_PARAMETER);
	}

	private void readCallstackParameter(JCoFunction function, BapiBean bean) {
		JCoTable callstackTable = function.getExportParameterList().getTable(BapiBean.CALLSTACK_PARAMETER);
		bean.callstack = new ArrayList<AlCallstackStr>();
		if (callstackTable.isEmpty()) {
			do {
				AlCallstackStr callstack = new AlCallstackStr();
				// Context
				JCoStructure contextStructure = callstackTable.getStructure(AlCallstackStr.CONTEXT);
				callstack.context = new AlContextStr();
				callstack.context.scope = contextStructure.getString(AlContextStr.SCOPE);
				// Context/codepos
				JCoStructure codeposStructure = contextStructure.getStructure(AlContextStr.CODEPOS);
				callstack.context.codepos = new AlCodepos();
				callstack.context.codepos.line = codeposStructure.getInt(AlCodepos.LINE);
				callstack.context.codepos.include = codeposStructure.getString(AlCodepos.INCLUDE);
				callstack.context.isNotInScope = (contextStructure.getChar(AlContextStr.NOT_IN_SCOPE) == ABAP_TRUE);
				callstack.context.isIgnored = (contextStructure.getChar(AlContextStr.IGNORED) == ABAP_TRUE);
				callstack.context.isGeneric = (contextStructure.getChar(AlContextStr.GENERIC) == ABAP_TRUE);
				// Caller
				JCoStructure objectStructure;
				objectStructure = callstackTable.getStructure(AlCallstackStr.CALLER);
				callstack.caller = new AlObjectStr();
				callstack.caller.type = objectStructure.getString(AlObjectStr.TYPE);
				callstack.caller.name = objectStructure.getString(AlObjectStr.NAME);
				callstack.caller.include = objectStructure.getString(AlObjectStr.INCLUDE);
				// Called
				objectStructure = callstackTable.getStructure(AlCallstackStr.CALLED);
				callstack.called = new AlObjectStr();
				callstack.called.type = objectStructure.getString(AlObjectStr.TYPE);
				callstack.called.name = objectStructure.getString(AlObjectStr.NAME);
				callstack.called.include = objectStructure.getString(AlObjectStr.INCLUDE);
				bean.callstack.add(callstack);
			} while (callstackTable.nextRow());
		}
	}

	private void readFindingsParameter(JCoFunction function, BapiBean bean) {
		JCoTable findingsTable = function.getExportParameterList().getTable(BapiBean.FINDINGS_PARAMETER);
		bean.findings = new ArrayList<AlFindingStr>();
		if (!findingsTable.isEmpty()) {
			do {
				AlFindingStr finding = new AlFindingStr();
				finding.pattern = findingsTable.getString(AlFindingStr.PATTERN);
				finding.findid = findingsTable.getString(AlFindingStr.FINDID);
				finding.isFindidMain = (findingsTable.getChar(AlFindingStr.FINDID_MAIN) == ABAP_TRUE);
				finding.codeposLine = findingsTable.getInt(AlFindingStr.CODEPOS_LINE);
				JCoStructure objectStructure = findingsTable.getStructure(AlFindingStr.OBJECT);
				finding.object = new AlObjectStr();
				finding.object.type = objectStructure.getString(AlObjectStr.TYPE);
				finding.object.name = objectStructure.getString(AlObjectStr.NAME);
				finding.object.include = objectStructure.getString(AlObjectStr.INCLUDE);
				finding.arbgb = findingsTable.getString(AlFindingStr.ARBGB);
				finding.msgnr = findingsTable.getString(AlFindingStr.MSGNR);
				finding.msgty = findingsTable.getString(AlFindingStr.MSGTY);
				finding.message = findingsTable.getString(AlFindingStr.MESSAGE);
				finding.param1 = findingsTable.getString(AlFindingStr.PARAM1);
				finding.param2 = findingsTable.getString(AlFindingStr.PARAM2);
				finding.paramlong = findingsTable.getString(AlFindingStr.PARAMLONG);
				bean.findings.add(finding);
			} while (findingsTable.nextRow());
		}
	}

	private void setPatternsParameter(JCoFunction function, BapiBean bean) {
		JCoTable patternsTable = function.getImportParameterList().getTable(BapiBean.PATTERNS_PARAMETER);
		for (String pattern : bean.patterns) {
			patternsTable.appendRow();
			patternsTable.setValue(0, pattern);
		}
		function.getImportParameterList().setValue(BapiBean.PATTERNS_PARAMETER, patternsTable);
	}

	private void setObjectsParameter(JCoFunction function, BapiBean bean) {
		JCoTable objectsTable = function.getImportParameterList().getTable(BapiBean.OBJECTS_PARAMETER);
		for (AlObjectStr object : bean.objects) {
			objectsTable.appendRow();
			objectsTable.setValue(AlObjectStr.TYPE, object.type);
			objectsTable.setValue(AlObjectStr.NAME, object.name);
			objectsTable.setValue(AlObjectStr.INCLUDE, object.include);
		}
		function.getImportParameterList().setValue(BapiBean.OBJECTS_PARAMETER, objectsTable);

	}

	private void setScopeParameter(JCoFunction function, BapiBean bean) {
		JCoStructure scopeStructure = function.getImportParameterList().getStructure(BapiBean.SCOPE_PARAMETER);
		scopeStructure.setValue(AlScopeStr.STANDARD, bean.scope.isStandard ? ABAP_TRUE : ABAP_FALSE);
		scopeStructure.setValue(AlScopeStr.PARTNER, bean.scope.isPartner ? ABAP_TRUE : ABAP_FALSE);
		scopeStructure.setValue(AlScopeStr.CUSTOM, bean.scope.isCustom ? ABAP_TRUE : ABAP_FALSE);
		scopeStructure.setValue(AlScopeStr.UNIVERSAL_DEPTH, bean.scope.universalDepth);
		scopeStructure.setValue(AlScopeStr.STANDARD_DEPTH, bean.scope.standardDepth);
		scopeStructure.setValue(AlScopeStr.PARTNER_DEPTH, bean.scope.partnerDepth);
		scopeStructure.setValue(AlScopeStr.CUSTOM_DEPTH, bean.scope.customDepth);
		scopeStructure.setValue(AlScopeStr.PARTNER_PRODUCER, bean.scope.isPartnerProducer ? ABAP_TRUE : ABAP_FALSE);
		function.getImportParameterList().setValue(BapiBean.SCOPE_PARAMETER, scopeStructure);
	}

}