package com.xiting.a43.backend.bapi.inspRunAdhoc;
import com.xiting.a43.backend.iBapiRunAdhocRunner;
public class BapiRunAdhocFactory {
     static public iBapiRunAdhocRunner getRunner() {
    	 return new BapiRunAdhocRunner();
     }
}
