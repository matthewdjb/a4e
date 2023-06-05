package com.xiting.a4e.backend.bapi.alViews;

import java.util.ArrayList;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.xiting.a4e.model.structures.AlAuthCheckStr;
import com.xiting.a4e.model.structures.AlAuthCheckSu24Str;
import com.xiting.a4e.model.structures.AlAuthMisStr;
import com.xiting.a4e.model.structures.AlEnhancementsStr;
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
			if (bean.authCheckFlag)
				readAuthBasicParameter();
			findMaxAuthCheckFields();
			if (bean.authCheckSu24Flag) {
				if (!(bean.authChecks == null))
					readAuthBasicParameter();
				readAuthCheckParameter();
			}
			if (bean.authMisFlag)
				readAuthMisParameter();
			if (bean.enhancementsFlag)
				readEnhancementsParameter();
			if (bean.statisticsFlag)
				readStatisticsParameter();
			bean.authCheckFlag = false;
			bean.authCheckSu24Flag = false;
			bean.authMisFlag = false;
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
			if (!authCheck.field08.isBlank() && bean.maxAuthCheckFields < 8) {
				bean.maxAuthCheckFields = 7;
			}
			if (!authCheck.field08.isBlank() && bean.maxAuthCheckFields < 7) {
				bean.maxAuthCheckFields = 6;
			}
			if (!authCheck.field08.isBlank() && bean.maxAuthCheckFields < 6) {
				bean.maxAuthCheckFields = 5;
			}
			if (!authCheck.field08.isBlank() && bean.maxAuthCheckFields < 5) {
				bean.maxAuthCheckFields = 4;
			}
			if (!authCheck.field08.isBlank() && bean.maxAuthCheckFields < 4) {
				bean.maxAuthCheckFields = 3;
			}
			if (!authCheck.field08.isBlank() && bean.maxAuthCheckFields < 3) {
				bean.maxAuthCheckFields = 2;
			}
			if (!authCheck.field08.isBlank() && bean.maxAuthCheckFields < 2) {
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
				bean.authMisFlag ? ABAP_TRUE : ABAP_FALSE);
		function.getImportParameterList().setValue(BapiViewsBean.ENHANCEMENTS_FLAG,
				bean.enhancementsFlag ? ABAP_TRUE : ABAP_FALSE);
		function.getImportParameterList().setValue(BapiViewsBean.STATISTICS_FLAG,
				bean.statisticsFlag ? ABAP_TRUE : ABAP_FALSE);
	}

	private void readAuthCheckParameter() {
		JCoTable authCheckTable = function.getExportParameterList().getTable(BapiViewsBean.AUTH_CHECK_PARAMETER);
		bean.authChecksSu24 = new ArrayList<>();
		if (!authCheckTable.isEmpty()) {
			do {
				AlAuthCheckSu24Str authCheck = new AlAuthCheckSu24Str();
				authCheck.alObject = new AlObjectStr();
				JCoStructure objectStructure = authCheckTable.getStructure(AlAuthCheckSu24Str.AL_OBJECT);
				authCheck.alObject.include = objectStructure.getString(AlObjectStr.INCLUDE);
				authCheck.alObject.name = objectStructure.getString(AlObjectStr.NAME);
				authCheck.alObject.type = objectStructure.getString(AlObjectStr.TYPE);
				authCheck.chktype = authCheckTable.getString(AlAuthCheckSu24Str.CHKTYPE);
				authCheck.depth = authCheckTable.getInt(AlAuthCheckSu24Str.DEPTH);
				authCheck.field = authCheckTable.getString(AlAuthCheckSu24Str.FIELD);
				authCheck.line = authCheckTable.getInt(AlAuthCheckSu24Str.LINE);
				authCheck.object = authCheckTable.getString(AlAuthCheckSu24Str.OBJECT);
				authCheck.scopeType = authCheckTable.getString(AlAuthCheckSu24Str.SCOPETYPE);
				authCheck.su24Append = authCheckTable.getString(AlAuthCheckSu24Str.SU24_APPEND);
				authCheck.su24Field = authCheckTable.getString(AlAuthCheckSu24Str.SU24_FIELD);
				authCheck.su24Hide = authCheckTable.getString(AlAuthCheckSu24Str.SU24_HIDE);
				authCheck.su24Jump = authCheckTable.getString(AlAuthCheckSu24Str.SU24_JUMP);
				authCheck.su24Low = authCheckTable.getString(AlAuthCheckSu24Str.SU24_LOW);
				authCheck.su24Name = authCheckTable.getString(AlAuthCheckSu24Str.SU24_NAME);
				authCheck.su24Object = authCheckTable.getString(AlAuthCheckSu24Str.SU24_OBJECT);
				authCheck.su24Text = authCheckTable.getString(AlAuthCheckSu24Str.SU24_TEXT);
				authCheck.su24Type = authCheckTable.getString(AlAuthCheckSu24Str.SU24_TYPE);
				authCheck.su24ObjAppend = authCheckTable.getString(AlAuthCheckSu24Str.SU24_OBJ_APPEND);
				bean.authChecksSu24.add(authCheck);
			} while (authCheckTable.nextRow());
		}
	}

	private void readAuthBasicParameter() {
		JCoTable authBasicTable = function.getExportParameterList().getTable(BapiViewsBean.AUTH_BASIC_PARAMETER);
		bean.authChecks = new ArrayList<>();
		if (!authBasicTable.isEmpty()) {
			do {
				AlAuthCheckStr authBasic = new AlAuthCheckStr();
				authBasic.field01 = authBasicTable.getString(AlAuthCheckStr.FIELD01);
				authBasic.field02 = authBasicTable.getString(AlAuthCheckStr.FIELD02);
				authBasic.field03 = authBasicTable.getString(AlAuthCheckStr.FIELD03);
				authBasic.field04 = authBasicTable.getString(AlAuthCheckStr.FIELD04);
				authBasic.field05 = authBasicTable.getString(AlAuthCheckStr.FIELD05);
				authBasic.field06 = authBasicTable.getString(AlAuthCheckStr.FIELD06);
				authBasic.field07 = authBasicTable.getString(AlAuthCheckStr.FIELD07);
				authBasic.field08 = authBasicTable.getString(AlAuthCheckStr.FIELD08);
				authBasic.field08 = authBasicTable.getString(AlAuthCheckStr.FIELD09);
				authBasic.field10 = authBasicTable.getString(AlAuthCheckStr.FIELD10);
				authBasic.flag = authBasicTable.getString(AlAuthCheckStr.FLAG);
				authBasic.flag01 = authBasicTable.getString(AlAuthCheckStr.FLAG01);
				authBasic.flag02 = authBasicTable.getString(AlAuthCheckStr.FLAG02);
				authBasic.flag03 = authBasicTable.getString(AlAuthCheckStr.FLAG03);
				authBasic.flag04 = authBasicTable.getString(AlAuthCheckStr.FLAG04);
				authBasic.flag05 = authBasicTable.getString(AlAuthCheckStr.FLAG05);
				authBasic.flag06 = authBasicTable.getString(AlAuthCheckStr.FLAG06);
				authBasic.flag07 = authBasicTable.getString(AlAuthCheckStr.FLAG07);
				authBasic.flag08 = authBasicTable.getString(AlAuthCheckStr.FLAG08);
				authBasic.flag09 = authBasicTable.getString(AlAuthCheckStr.FLAG09);
				authBasic.flag10 = authBasicTable.getString(AlAuthCheckStr.FLAG10);
				authBasic.line = authBasicTable.getInt(AlAuthCheckStr.LINE);
				authBasic.object = authBasicTable.getString(AlAuthCheckStr.OBJECT);
				authBasic.scopetype = authBasicTable.getString(AlAuthCheckStr.SCOPETYPE);
				authBasic.val01 = authBasicTable.getString(AlAuthCheckStr.VAL01);
				authBasic.val02 = authBasicTable.getString(AlAuthCheckStr.VAL02);
				authBasic.val03 = authBasicTable.getString(AlAuthCheckStr.VAL03);
				authBasic.val04 = authBasicTable.getString(AlAuthCheckStr.VAL04);
				authBasic.val05 = authBasicTable.getString(AlAuthCheckStr.VAL05);
				authBasic.val06 = authBasicTable.getString(AlAuthCheckStr.VAL06);
				authBasic.val07 = authBasicTable.getString(AlAuthCheckStr.VAL07);
				authBasic.val08 = authBasicTable.getString(AlAuthCheckStr.VAL08);
				authBasic.val09 = authBasicTable.getString(AlAuthCheckStr.VAL09);
				authBasic.val10 = authBasicTable.getString(AlAuthCheckStr.VAL10);
				bean.authChecks.add(authBasic);
			} while (authBasicTable.nextRow());
		}
	}

	private void readAuthMisParameter() {
		JCoTable authMisTable = function.getExportParameterList().getTable(BapiViewsBean.AUTH_MIS_PARAMETER);
		bean.authMis = new ArrayList<>();
		if (!authMisTable.isEmpty()) {
			do {
				AlAuthMisStr authMis = new AlAuthMisStr();
				JCoStructure objectStructure = authMisTable.getStructure(AlAuthMisStr.AL_OBJECT);
				authMis.alObject = new AlObjectStr();
				authMis.alObject.include = objectStructure.getString(AlObjectStr.INCLUDE);
				authMis.alObject.name = objectStructure.getString(AlObjectStr.NAME);
				authMis.alObject.type = objectStructure.getString(AlObjectStr.TYPE);
				authMis.depth = authMisTable.getInt(AlAuthMisStr.DEPTH);
				authMis.line = authMisTable.getInt(AlAuthMisStr.LINE);
				authMis.scopetype = authMisTable.getString(AlAuthMisStr.SCOPETYPE);
				authMis.su24Append = authMisTable.getString(AlAuthMisStr.SU24_APPEND);
				authMis.su24Field = authMisTable.getString(AlAuthMisStr.SU24_FIELD);
				authMis.su24Hide = authMisTable.getString(AlAuthMisStr.SU24_HIDE);
				authMis.su24Jump = authMisTable.getString(AlAuthMisStr.SU24_JUMP);
				authMis.su24Low = authMisTable.getString(AlAuthMisStr.SU24_LOW);
				authMis.su24Name = authMisTable.getString(AlAuthMisStr.SU24_NAME);
				authMis.su24Object = authMisTable.getString(AlAuthMisStr.SU24_OBJECT);
				authMis.su24Text = authMisTable.getString(AlAuthMisStr.SU24_TEXT);
				authMis.su24Type = authMisTable.getString(AlAuthMisStr.SU24_TYPE);
				authMis.su24ObjAppend = authMisTable.getString(AlAuthMisStr.SU24_OBJ_APPEND);
				bean.authMis.add(authMis);
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
