package com.xiting.a4e.backend.bapi.inspRunAdhoc;


public class BapiRunAdhocFactory {
    static public iBapiRunAdhocRunner getRunner() {
   	 return new BapiRunAdhocRunner();
    }
}