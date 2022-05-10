package com.xiting.a43.backend;

import java.util.ArrayList;

import com.sap.conn.jco.JCoDestination;
import com.xiting.a4e.model.structures.AlObjectStr;

public interface iBapiRunAdhocRunner {

	void run(JCoDestination project, ArrayList<AlObjectStr> items);
  
}
