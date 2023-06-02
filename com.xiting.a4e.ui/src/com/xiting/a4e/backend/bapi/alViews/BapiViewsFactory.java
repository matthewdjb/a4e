package com.xiting.a4e.backend.bapi.alViews;

public class BapiViewsFactory {
   static public IBapiViewsRunner getRunner() {
	   return new BapiViewsRunner();
   }
}
