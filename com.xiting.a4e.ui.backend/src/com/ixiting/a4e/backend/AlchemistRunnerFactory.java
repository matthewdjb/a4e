package com.ixiting.a4e.backend;

public class AlchemistRunnerFactory {
   static public IAlchemistRunner get() {
	   return new AlchemistRunner();
   }
}
