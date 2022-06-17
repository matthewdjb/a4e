package com.xiting.a4e.backend.bapi.inspRunAdhoc;


public class BapiRunAdhocFactory {
    static public IBapiRunAdhocRunner getRunner() {
   	 return new BapiRunAdhocRunner();
    }
}