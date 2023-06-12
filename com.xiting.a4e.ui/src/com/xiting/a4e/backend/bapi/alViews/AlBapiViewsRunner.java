package com.xiting.a4e.backend.bapi.alViews;

import java.util.ArrayList;
import java.util.Collections;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.xiting.a4e.model.structures.AlAuthCheckStr;
import com.xiting.a4e.model.structures.AlAuthCheckSu24Str;
import com.xiting.a4e.model.structures.AlEnhancementsStr;
import com.xiting.a4e.model.structures.AlMissingAuthStr;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.AlStatisticsStr;
import com.xiting.a4e.model.structures.BapiViewsBean;

public class AlBapiViewsRunner {
	private static final String VIEWS_BAPI = "/XITING/AL_BAPI_VIEWS";
	private final static char ABAP_TRUE = 'X';
	private final static char ABAP_FALSE = ' ';
	private BapiViewsBean bean;
	private JCoFunction function;
	private JCoDestination destination;
	private JCoTable callStackTable;
	private JCoTable findingsTable;

	AlBapiViewsRunner(BapiViewsBean bean, JCoDestination destination, JCoTable callStackTable, JCoTable findingsTable)
			throws JCoException {
		this.bean = bean;
		this.function = destination.getRepository().getFunction(VIEWS_BAPI);
		this.destination = destination;
		this.callStackTable = callStackTable;
		this.findingsTable = findingsTable;
	}

	void run() {
		try {
			setCallStackParameter();
			setFindingsParameter();
			setFlagParameters();
			function.execute(destination);
			if (bean.authCheckFlag) {
				readAuthChecksParameter();
				findMaxAuthCheckFields();
			}
			if (bean.authCheckSu24Flag)
				readAuthCheckSu24Parameter();
			if (bean.missingAuthsFlag)
				readMissngAuthsParameter();
			if (bean.enhancementsFlag)
				readEnhancementsParameter();
			if (bean.statisticsFlag)
				readStatisticsParameter();
			bean.authCheckFlag = false;
			bean.authCheckSu24Flag = false;
			bean.missingAuthsFlag = false;
			bean.enhancementsFlag = false;
			bean.statisticsFlag = false;
		} catch (JCoException e) {
			e.printStackTrace();
		}
	}

	private void findMaxAuthCheckFields() {
		bean.maxAuthCheckFields = 0;
		for (AlAuthCheckStr authCheck : bean.authChecks) {
			if (!authCheck.field10.isBlank()) {
				bean.maxAuthCheckFields = 10;
				return;
			}
			if (!authCheck.field09.isBlank()) {
				bean.maxAuthCheckFields = 9;
			}
			if (!authCheck.field08.isBlank() && bean.maxAuthCheckFields < 9) {
				bean.maxAuthCheckFields = 8;
			}
			if (!authCheck.field07.isBlank() && bean.maxAuthCheckFields < 8) {
				bean.maxAuthCheckFields = 7;
			}
			if (!authCheck.field06.isBlank() && bean.maxAuthCheckFields < 7) {
				bean.maxAuthCheckFields = 6;
			}
			if (!authCheck.field05.isBlank() && bean.maxAuthCheckFields < 6) {
				bean.maxAuthCheckFields = 5;
			}
			if (!authCheck.field04.isBlank() && bean.maxAuthCheckFields < 5) {
				bean.maxAuthCheckFields = 4;
			}
			if (!authCheck.field03.isBlank() && bean.maxAuthCheckFields < 4) {
				bean.maxAuthCheckFields = 3;
			}
			if (!authCheck.field02.isBlank() && bean.maxAuthCheckFields < 3) {
				bean.maxAuthCheckFields = 2;
			}
			if (!authCheck.field01.isBlank() && bean.maxAuthCheckFields < 2) {
				bean.maxAuthCheckFields = 1;
			}
		}
	}

	private void setCallStackParameter() {
		function.getImportParameterList().setValue(BapiViewsBean.CALLSTACK_PARAMETER, callStackTable);
	}

	private void setFindingsParameter() {
		function.getImportParameterList().setValue(BapiViewsBean.FINDINGS_PARAMETER, findingsTable);
	}

	private void setFlagParameters() {
		function.getImportParameterList().setValue(BapiViewsBean.AUTH_CHECK_FLAG,
				bean.authCheckFlag ? ABAP_TRUE : ABAP_FALSE);
		function.getImportParameterList().setValue(BapiViewsBean.AUTH_CHECK_SU24_FLAG,
				bean.authCheckSu24Flag ? ABAP_TRUE : ABAP_FALSE);
		function.getImportParameterList().setValue(BapiViewsBean.AUTH_MIS_FLAG,
				bean.missingAuthsFlag ? ABAP_TRUE : ABAP_FALSE);
		function.getImportParameterList().setValue(BapiViewsBean.ENHANCEMENTS_FLAG,
				bean.enhancementsFlag ? ABAP_TRUE : ABAP_FALSE);
		function.getImportParameterList().setValue(BapiViewsBean.STATISTICS_FLAG,
				bean.statisticsFlag ? ABAP_TRUE : ABAP_FALSE);
	}

	private void readAuthCheckSu24Parameter() {
		JCoTable authCheckTable = function.getExportParameterList().getTable(BapiViewsBean.AUTH_CHECK_SU24_PARAMETER);
		bean.authChecksSu24 = new ArrayList<>();
		if (!authCheckTable.isEmpty()) {
			do {
				AlAuthCheckSu24Str authCheckSu24 = new AlAuthCheckSu24Str();
				authCheckSu24.alObject = new AlObjectStr();
				authCheckSu24.alObject.include = authCheckTable.getString(AlAuthCheckStr.INCLUDE);
				authCheckSu24.alObject.name = authCheckTable.getString(AlAuthCheckStr.NAME);
				authCheckSu24.alObject.type = authCheckTable.getString(AlAuthCheckStr.TYPE);
				authCheckSu24.chktype = authCheckTable.getString(AlAuthCheckSu24Str.CHKTYPE);
				authCheckSu24.depth = authCheckTable.getInt(AlAuthCheckSu24Str.DEPTH);
				authCheckSu24.field = authCheckTable.getString(AlAuthCheckSu24Str.FIELD);
				authCheckSu24.line = authCheckTable.getInt(AlAuthCheckSu24Str.LINE);
				authCheckSu24.object = authCheckTable.getString(AlAuthCheckSu24Str.OBJECT);
				authCheckSu24.scopeType = authCheckTable.getString(AlAuthCheckSu24Str.SCOPETYPE);
				authCheckSu24.su24Field = authCheckTable.getString(AlAuthCheckSu24Str.SU24_FIELD);
				authCheckSu24.su24Low = authCheckTable.getString(AlAuthCheckSu24Str.SU24_LOW);
				authCheckSu24.su24Name = authCheckTable.getString(AlAuthCheckSu24Str.SU24_NAME);
				authCheckSu24.su24NoCheck = authCheckTable.getString(AlAuthCheckSu24Str.SU24_NOCHECK);
				authCheckSu24.su24Object = authCheckTable.getString(AlAuthCheckSu24Str.SU24_OBJECT);
				authCheckSu24.su24Text = authCheckTable.getString(AlAuthCheckSu24Str.SU24_TEXT);
				authCheckSu24.su24Type = authCheckTable.getString(AlAuthCheckSu24Str.SU24_TYPE);
				bean.authChecksSu24.add(authCheckSu24);
			} while (authCheckTable.nextRow());
		}
	}

	private void readAuthChecksParameter() {
		JCoTable authChecksTable = function.getExportParameterList().getTable(BapiViewsBean.AUTH_CHECKS_PARAMETER);
		bean.authChecks = new ArrayList<>();
		if (!authChecksTable.isEmpty()) {
			do {
				AlAuthCheckStr authCheck = new AlAuthCheckStr();
				authCheck.field01 = authChecksTable.getString(AlAuthCheckStr.FIELD01);
				authCheck.field02 = authChecksTable.getString(AlAuthCheckStr.FIELD02);
				authCheck.field03 = authChecksTable.getString(AlAuthCheckStr.FIELD03);
				authCheck.field04 = authChecksTable.getString(AlAuthCheckStr.FIELD04);
				authCheck.field05 = authChecksTable.getString(AlAuthCheckStr.FIELD05);
				authCheck.field06 = authChecksTable.getString(AlAuthCheckStr.FIELD06);
				authCheck.field07 = authChecksTable.getString(AlAuthCheckStr.FIELD07);
				authCheck.field08 = authChecksTable.getString(AlAuthCheckStr.FIELD08);
				authCheck.field09 = authChecksTable.getString(AlAuthCheckStr.FIELD09);
				authCheck.field10 = authChecksTable.getString(AlAuthCheckStr.FIELD10);
				authCheck.flag = authChecksTable.getString(AlAuthCheckStr.FLAG);
				authCheck.flag01 = authChecksTable.getString(AlAuthCheckStr.FLAG01);
				authCheck.flag02 = authChecksTable.getString(AlAuthCheckStr.FLAG02);
				authCheck.flag03 = authChecksTable.getString(AlAuthCheckStr.FLAG03);
				authCheck.flag04 = authChecksTable.getString(AlAuthCheckStr.FLAG04);
				authCheck.flag05 = authChecksTable.getString(AlAuthCheckStr.FLAG05);
				authCheck.flag06 = authChecksTable.getString(AlAuthCheckStr.FLAG06);
				authCheck.flag07 = authChecksTable.getString(AlAuthCheckStr.FLAG07);
				authCheck.flag08 = authChecksTable.getString(AlAuthCheckStr.FLAG08);
				authCheck.flag09 = authChecksTable.getString(AlAuthCheckStr.FLAG09);
				authCheck.flag10 = authChecksTable.getString(AlAuthCheckStr.FLAG10);
				authCheck.line = authChecksTable.getInt(AlAuthCheckStr.LINE);
				authCheck.object = authChecksTable.getString(AlAuthCheckStr.OBJECT);
				authCheck.alObject = new AlObjectStr();
				authCheck.alObject.include = authChecksTable.getString(AlAuthCheckStr.INCLUDE);
				authCheck.alObject.name = authChecksTable.getString(AlAuthCheckStr.NAME);
				authCheck.alObject.type = authChecksTable.getString(AlAuthCheckStr.TYPE);
				authCheck.scopetype = authChecksTable.getString(AlAuthCheckStr.SCOPETYPE);
				authCheck.val01 = authChecksTable.getString(AlAuthCheckStr.VAL01);
				authCheck.val02 = authChecksTable.getString(AlAuthCheckStr.VAL02);
				authCheck.val03 = authChecksTable.getString(AlAuthCheckStr.VAL03);
				authCheck.val04 = authChecksTable.getString(AlAuthCheckStr.VAL04);
				authCheck.val05 = authChecksTable.getString(AlAuthCheckStr.VAL05);
				authCheck.val06 = authChecksTable.getString(AlAuthCheckStr.VAL06);
				authCheck.val07 = authChecksTable.getString(AlAuthCheckStr.VAL07);
				authCheck.val08 = authChecksTable.getString(AlAuthCheckStr.VAL08);
				authCheck.val09 = authChecksTable.getString(AlAuthCheckStr.VAL09);
				authCheck.val10 = authChecksTable.getString(AlAuthCheckStr.VAL10);
				bean.authChecks.add(authCheck);
			} while (authChecksTable.nextRow());
		}
		Collections.sort(bean.authChecks, (a, b) -> b.compareTo(a));
	}

	private void readMissngAuthsParameter() {
		JCoTable authMisTable = function.getExportParameterList().getTable(BapiViewsBean.MISSING_AUTHS_PARAMETER);
		bean.missingAuths = new ArrayList<>();
		if (!authMisTable.isEmpty()) {
			do {
				AlMissingAuthStr missingAuth = new AlMissingAuthStr();
				missingAuth.alObject = new AlObjectStr();
				missingAuth.alObject.include = authMisTable.getString(AlMissingAuthStr.INCLUDE);
				missingAuth.alObject.name = authMisTable.getString(AlMissingAuthStr.NAME);
				missingAuth.alObject.type = authMisTable.getString(AlMissingAuthStr.TYPE);
				missingAuth.depth = authMisTable.getInt(AlMissingAuthStr.DEPTH);
				missingAuth.line = authMisTable.getInt(AlMissingAuthStr.LINE);
				missingAuth.scopetype = authMisTable.getString(AlMissingAuthStr.SCOPETYPE);
				missingAuth.su24Field = authMisTable.getString(AlMissingAuthStr.SU24_FIELD);
				missingAuth.su24Low = authMisTable.getString(AlMissingAuthStr.SU24_LOW);
				missingAuth.su24Name = authMisTable.getString(AlMissingAuthStr.SU24_NAME);
				missingAuth.su24Object = authMisTable.getString(AlMissingAuthStr.SU24_OBJECT);
				missingAuth.su24Text = authMisTable.getString(AlMissingAuthStr.SU24_TEXT);
				missingAuth.su24Type = authMisTable.getString(AlMissingAuthStr.SU24_TYPE);
				bean.missingAuths.add(missingAuth);
			} while (authMisTable.nextRow());
		}
	}

	private void readEnhancementsParameter() {
		JCoTable enhancementTable = function.getExportParameterList().getTable(BapiViewsBean.ENHANCEMENTS_PARAMETER);
		bean.enhancements = new ArrayList<>();
		if (!enhancementTable.isEmpty()) {
			do {
				AlEnhancementsStr enhancement = new AlEnhancementsStr();
				enhancement.defDevclass = enhancementTable.getString(AlEnhancementsStr.DEF_DEVCLASS);
				enhancement.defName = enhancementTable.getString(AlEnhancementsStr.DEF_NAME);
				enhancement.defPsPosid = enhancementTable.getString(AlEnhancementsStr.DEF_PS_POSID);
				enhancement.defText = enhancementTable.getString(AlEnhancementsStr.DEF_TEXT);
				enhancement.defType = enhancementTable.getString(AlEnhancementsStr.DEF_TYPE);
				enhancement.implActive = enhancementTable.getString(AlEnhancementsStr.IMPL_ACTIVE);
				enhancement.implDevclass = enhancementTable.getString(AlEnhancementsStr.IMPL_DEVCLASS);
				enhancement.implFind = enhancementTable.getInt(AlEnhancementsStr.IMPL_FIND);
				enhancement.implName = enhancementTable.getString(AlEnhancementsStr.IMPL_NAME);
				enhancement.implPsPosid = enhancementTable.getString(AlEnhancementsStr.IMPL_PS_POSID);
				enhancement.implScanned = enhancementTable.getString(AlEnhancementsStr.IMPL_SCANNED);
				enhancement.implText = enhancementTable.getString(AlEnhancementsStr.IMPL_TEXT);
				enhancement.implType = enhancementTable.getString(AlEnhancementsStr.IMPL_TYPE);
				bean.enhancements.add(enhancement);
			} while (enhancementTable.nextRow());
		}
	}

	private void readStatisticsParameter() {
		JCoTable statisticsTable = function.getExportParameterList().getTable(BapiViewsBean.ENHANCEMENTS_PARAMETER);
		bean.statistics = new ArrayList<>();
		if (!statisticsTable.isEmpty()) {
			do {
				AlStatisticsStr statistic = new AlStatisticsStr();
				statistic.codeposInclude = statisticsTable.getString(AlStatisticsStr.CODEPOS_INCLUDE);
				statistic.codeposLine = statisticsTable.getInt(AlStatisticsStr.CODEPOS_LINE);
				statistic.colid = statisticsTable.getInt(AlStatisticsStr.COLID);
				statistic.colparid = statisticsTable.getInt(AlStatisticsStr.COLPARID);
				statistic.findid = statisticsTable.getString(AlStatisticsStr.FINDID);
				statistic.green = statisticsTable.getInt(AlStatisticsStr.GREEN);
				statistic.objid = statisticsTable.getString(AlStatisticsStr.OBJID);
				statistic.otype = statisticsTable.getString(AlStatisticsStr.OTYPE);
				statistic.parent = statisticsTable.getString(AlStatisticsStr.PARENT);
				statistic.pattern = statisticsTable.getString(AlStatisticsStr.PATTERN);
				statistic.red = statisticsTable.getInt(AlStatisticsStr.RED);
				statistic.text = statisticsTable.getString(AlStatisticsStr.TEXT);
				statistic.yellow = statisticsTable.getInt(AlStatisticsStr.YELLOW);
				bean.statistics.add(statistic);
			} while (statisticsTable.nextRow());
		}
	}

}
