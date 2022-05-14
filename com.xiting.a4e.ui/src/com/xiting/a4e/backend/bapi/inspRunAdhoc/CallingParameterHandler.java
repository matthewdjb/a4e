package com.xiting.a4e.backend.bapi.inspRunAdhoc;

import java.util.ArrayList;

import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.AlScopeStr;
import com.xiting.a4e.model.structures.BapiBean;

public class CallingParameterHandler {
	public void setScopeParameter(BapiBean bean) {
		bean.scope = new AlScopeStr();
		bean.scope.scanStandardCode = true;
		bean.scope.scanPartnerCode = true;
		bean.scope.scanCustomCode = true;
		bean.scope.universalDepth = 2;
		bean.scope.standardDepth = 2;
		bean.scope.partnerDepth = 2;
		bean.scope.customDepth = 2;
		bean.scope.isPartnerProducer = false;
	}
	public void setItems(BapiBean bean, ArrayList<AlObjectStr> items) {
		bean.objects = items;
	}

	public void setPatternsParameter(BapiBean bean) {
		bean.patterns = new ArrayList<String>();
		bean.patterns.add("/XITING/AL_PTRN_CRIT_STMNTS");
		bean.patterns.add("/XITING/AL_PTRN_FUNCTION_CALL");
		bean.patterns.add("/XITING/AL_PTRN_PERFORM_CALL");
		bean.patterns.add("/XITING/AL_PTRN_SUBMIT");
		bean.patterns.add("/XITING/AL_PTRN_STATMETH_CALL");
		bean.patterns.add("/XITING/AL_PTRN_INSTMETH_CALL");
		bean.patterns.add("/XITING/AL_PTRN_LOCMETH_CALL");
		bean.patterns.add("/XITING/AL_PTRN_SCREEN_CALL");
		bean.patterns.add("/XITING/AL_PTRN_MODULE_CALL");
		bean.patterns.add("/XITING/AL_PTRN_CALL_DIALOG");
		bean.patterns.add("/XITING/AL_PTRN_TCODE_CALL");
		bean.patterns.add("/XITING/AL_PTRN_BADI");
		bean.patterns.add("/XITING/AL_PTRN_ENHANCEMENTS");
		bean.patterns.add("/XITING/AL_PTRN_LOGDB_CALL");
		bean.patterns.add("/XITING/AL_PTRN_AUTH_CHECK");
		bean.patterns.add("/XITING/AL_PTRN_DATASET");
		bean.patterns.add("/XITING/AL_PTRN_SACF");
		bean.patterns.add("/XITING/AL_PTRN_TCODE_AUTHC");
		bean.patterns.add("/XITING/AL_PTRN_HR_ABAP");
		bean.patterns.add("/XITING/AL_PTRN_SQL_STMNT");
		bean.patterns.add("/XITING/AL_PTRN_SYST_FIELDS");
		bean.patterns.add("/XITING/AL_PTRN_CRIT_STMNTS");
		bean.patterns.add("/XITING/AL_PTRN_KERNEL_CALL");
		bean.patterns.add("/XITING/AL_PTRN_DEBUG");
		bean.patterns.add("/XITING/AL_PTRN_MACROS");
		bean.patterns.add("/XITING/AL_PTRN_PID");
		bean.patterns.add("/XITING/AL_PTRN_SEARCH");
	}
}
