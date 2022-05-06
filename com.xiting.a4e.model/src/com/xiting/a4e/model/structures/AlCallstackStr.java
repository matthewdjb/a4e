package com.xiting.a4e.model.structures;

public class AlCallstackStr {
	public final static String CONTEXT = "CONTEXT";
	public final static String CALLER = "CALLER";
	public final static String CALLED = "CALLED";
	
	public AlContextStr context;
	public AlObjectStr caller;
	public AlObjectStr called;
}
