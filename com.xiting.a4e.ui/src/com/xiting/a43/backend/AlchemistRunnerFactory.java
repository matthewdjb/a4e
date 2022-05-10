package com.xiting.a43.backend;

public class AlchemistRunnerFactory {
   static public IAlchemistRunner get() {
	   return new AlchemistRunner();
   }
}
