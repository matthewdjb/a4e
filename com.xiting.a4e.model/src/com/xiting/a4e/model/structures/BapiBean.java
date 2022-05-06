package com.xiting.a4e.model.structures;

import java.util.ArrayList;

public class BapiBean {
	public final static String SCOPE_PARAMETER = "IS_SCOPE";
	public final static String OBJECTS_PARAMETER = "IT_OBJECTS";
	public final static String PATTERNS_PARAMETER = "IT_PATTERNS";
	public final static String CALLSTACK_PARAMETER = "ET_CALLSTACK";
	public final static String FINDINGS_PARAMETER = "ET_FINDINGS";
	public final static String RETURN_PARAMETER = "ET_RETURN";
	public final static String RETCODE_PARAMETER = "EV_RETCODE";
	
	public AlScopeStr scope;
	public ArrayList<AlObjectStr> objects;
	public ArrayList<String> patterns;
	public ArrayList<AlCallstackStr> callstack;
	public ArrayList<AlFindingStr> findings;
	public ArrayList<BapiRet2> bapiReturn;
	public int retcode;
}
