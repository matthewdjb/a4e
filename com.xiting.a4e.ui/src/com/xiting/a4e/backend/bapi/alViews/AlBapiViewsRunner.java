package com.xiting.a4e.backend.bapi.alViews;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.xiting.a4e.model.structures.BapiViewsBean;

public class AlBapiViewsRunner {
	private static final String VIEWS_BAPI = "/XITING/AL_BAPI_VIEWS";
	private BapiViewsBean bean;
	private JCoFunction function;
	private JCoDestination destination;

	AlBapiViewsRunner(BapiViewsBean bean, JCoDestination destination) throws JCoException {
		this.bean = bean;
		this.function = destination.getRepository().getFunction(VIEWS_BAPI);
		this.destination = destination;
	}

	void run() {
		try {
			setCallStackParameter();
			setFindingsParameter();
			setFlagParameters();
			function.execute(destination);
			readAuthCheckParameter();
			readAuthBasicParameter();
			readAuthMisParameter();
			readEnhancementsParameter();
			readStatisticsParameter();
		} catch (JCoException e) {
			e.printStackTrace();
		}
	}

	private void readStatisticsParameter() {
		// TODO Auto-generated method stub
		
	}

	private void readEnhancementsParameter() {
		// TODO Auto-generated method stub
		
	}

	private void readAuthMisParameter() {
		// TODO Auto-generated method stub
		
	}

	private void readAuthBasicParameter() {
		// TODO Auto-generated method stub
		
	}

	private void readAuthCheckParameter() {
		// TODO Auto-generated method stub
		
	}

	private void setFlagParameters() {
		// TODO Auto-generated method stub
		
	}

	private void setFindingsParameter() {
		// TODO Auto-generated method stub
		
	}

	private void setCallStackParameter() {
		// TODO Auto-generated method stub
		
	}
}
