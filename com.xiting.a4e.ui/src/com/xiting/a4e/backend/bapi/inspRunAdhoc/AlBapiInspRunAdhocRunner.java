package com.xiting.a4e.backend.bapi.inspRunAdhoc;

import java.util.ArrayList;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.CallStack;
import com.xiting.a4e.model.structures.AlCallStackStr;
import com.xiting.a4e.model.structures.AlCodepos;
import com.xiting.a4e.model.structures.AlContextStr;
import com.xiting.a4e.model.structures.AlFindingStr;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.AlPatternStr;
import com.xiting.a4e.model.structures.AlScopeStr;
import com.xiting.a4e.model.structures.BapiBean;
import com.xiting.a4e.model.structures.BapiRet2;

class AlBapiInspRunAdhocRunner {
	private static final String ALCHEMIST_BADI = "/XITING/AL_BAPI_INSP_RUN_ADHOC"; //$NON-NLS-1$
	private final static char ABAP_TRUE = 'X';
	private final static char ABAP_FALSE = ' ';

	private BapiBean bean;
	private JCoDestination destination;
	private JCoFunction function;

	AlBapiInspRunAdhocRunner(BapiBean bean, JCoDestination destination) throws JCoException {
		this.bean = bean;
		this.function = destination.getRepository().getFunction(ALCHEMIST_BADI);
		this.destination = destination;
	}

	void run() {
		try {
			setScopeParameter();
			setObjectsParameter();
			setPatternsParameter();
			function.execute(destination);
			readFindingsParameter();
			readCallstackParameter();
			readReturnParameter();
			readRetcodeParameter();
		} catch (JCoException e) {
			e.printStackTrace();
		}
	}

	private void readReturnParameter() {
		JCoTable bapiret2Table = function.getExportParameterList().getTable(BapiBean.RETURN_PARAMETER);
		bean.bapiReturn = new ArrayList<>();
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

	private void readRetcodeParameter() {
		bean.retcode = function.getExportParameterList().getInt(BapiBean.RETCODE_PARAMETER);
	}

	private void readCallstackParameter() {
		JCoTable callstackTable = function.getExportParameterList().getTable(BapiBean.CALLSTACK_PARAMETER);
		if (bean.callStack == null)
			bean.callStack = new CallStack();
		if (!callstackTable.isEmpty()) {
			do {
				readCallStackRecord(callstackTable);
			} while (callstackTable.nextRow());
			bean.callStack.buildTree();
			bean.callStack.addFindings(bean.findings);
		}
	}

	private void readCallStackRecord(JCoTable callstackTable) {
		AlCallStackStr callStackEntry = new AlCallStackStr();
		// Context
		JCoStructure contextStructure = callstackTable.getStructure(AlCallStackStr.CONTEXT);
		callStackEntry.context = new AlContextStr();
		callStackEntry.context.scope = contextStructure.getString(AlContextStr.SCOPE);
		// Context/codepos
		JCoStructure codeposStructure = contextStructure.getStructure(AlContextStr.CODEPOS);
		callStackEntry.context.codepos = new AlCodepos();
		callStackEntry.context.codepos.line = codeposStructure.getInt(AlCodepos.LINE);
		callStackEntry.context.codepos.include = codeposStructure.getString(AlCodepos.INCLUDE);
		callStackEntry.context.isNotInScope = (contextStructure
				.getChar(AlContextStr.NOT_IN_SCOPE) == ABAP_TRUE);
		callStackEntry.context.isIgnored = (contextStructure.getChar(AlContextStr.IGNORED) == ABAP_TRUE);
		callStackEntry.context.isGeneric = (contextStructure.getChar(AlContextStr.GENERIC) == ABAP_TRUE);
		// Caller
		JCoStructure callerObjectStructure;
		callerObjectStructure = callstackTable.getStructure(AlCallStackStr.CALLER);
		callStackEntry.caller = new AlObjectStr();
		callStackEntry.caller.type = callerObjectStructure.getString(AlObjectStr.TYPE);
		callStackEntry.caller.name = callerObjectStructure.getString(AlObjectStr.NAME);
		callStackEntry.caller.include = callerObjectStructure.getString(AlObjectStr.INCLUDE);
		// Called
		JCoStructure calledObjectStructure;
		calledObjectStructure = callstackTable.getStructure(AlCallStackStr.CALLED);
		callStackEntry.called = new AlObjectStr();
		callStackEntry.called.type = calledObjectStructure.getString(AlObjectStr.TYPE);
		callStackEntry.called.name = calledObjectStructure.getString(AlObjectStr.NAME);
		callStackEntry.called.include = calledObjectStructure.getString(AlObjectStr.INCLUDE);
		bean.callStack.add(callStackEntry);
	}

	private void readFindingsParameter() {
		JCoTable findingsTable = function.getExportParameterList().getTable(BapiBean.FINDINGS_PARAMETER);
		bean.findings = new ArrayList<>();
		ArrayList<AlPatternStr> patterns = AlchemistController.factory().getPatterns();
		if (!findingsTable.isEmpty()) {
			do {
				AlFindingStr finding = new AlFindingStr();
				String className = findingsTable.getString(AlFindingStr.PATTERN);
				AlPatternStr pattern = patterns.stream().filter(p -> className.equals(p.className)).findFirst()
						.orElse(null);
				if (pattern == null)
					finding.pattern = className;
				else
					finding.pattern = pattern.descr;
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

	private void setPatternsParameter() {
		JCoTable patternsTable = function.getImportParameterList().getTable(BapiBean.PATTERNS_PARAMETER);
		for (String pattern : bean.patterns) {
			patternsTable.appendRow();
			patternsTable.setValue(0, pattern);
		}
		function.getImportParameterList().setValue(BapiBean.PATTERNS_PARAMETER, patternsTable);
	}

	private void setObjectsParameter() {
		JCoTable objectsTable = function.getImportParameterList().getTable(BapiBean.OBJECTS_PARAMETER);
		for (AlObjectStr object : bean.objects) {
			objectsTable.appendRow();
			objectsTable.setValue(AlObjectStr.TYPE, object.type);
			objectsTable.setValue(AlObjectStr.NAME, object.name);
			objectsTable.setValue(AlObjectStr.INCLUDE, object.include);
		}
		function.getImportParameterList().setValue(BapiBean.OBJECTS_PARAMETER, objectsTable);

	}

	private void setScopeParameter() {
		JCoStructure scopeStructure = function.getImportParameterList().getStructure(BapiBean.SCOPE_PARAMETER);
		scopeStructure.setValue(AlScopeStr.STANDARD, bean.scope.scanStandardCode ? ABAP_TRUE : ABAP_FALSE);
		scopeStructure.setValue(AlScopeStr.PARTNER, bean.scope.scanPartnerCode ? ABAP_TRUE : ABAP_FALSE);
		scopeStructure.setValue(AlScopeStr.CUSTOM, bean.scope.scanCustomCode ? ABAP_TRUE : ABAP_FALSE);
		scopeStructure.setValue(AlScopeStr.UNIVERSAL_DEPTH, bean.scope.universalDepth);
		scopeStructure.setValue(AlScopeStr.STANDARD_DEPTH, bean.scope.standardDepth);
		scopeStructure.setValue(AlScopeStr.PARTNER_DEPTH, bean.scope.partnerDepth);
		scopeStructure.setValue(AlScopeStr.CUSTOM_DEPTH, bean.scope.customDepth);
		scopeStructure.setValue(AlScopeStr.PARTNER_PRODUCER,
				bean.scope.producerNameSpacesOnly ? ABAP_TRUE : ABAP_FALSE);
		function.getImportParameterList().setValue(BapiBean.SCOPE_PARAMETER, scopeStructure);
	}

}