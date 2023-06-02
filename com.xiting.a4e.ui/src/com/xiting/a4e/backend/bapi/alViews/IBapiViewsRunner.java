package com.xiting.a4e.backend.bapi.alViews;

import java.util.ArrayList;

import com.sap.conn.jco.JCoDestination;
import com.xiting.a4e.model.structures.AlCallStackStr;
import com.xiting.a4e.model.structures.AlFindingStr;

public interface IBapiViewsRunner {

	void run(JCoDestination project, ArrayList<AlFindingStr> findings, AlCallStackStr callStack);

}
