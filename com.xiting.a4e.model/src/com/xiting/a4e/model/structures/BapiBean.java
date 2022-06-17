package com.xiting.a4e.model.structures;

import java.util.ArrayList;

import com.xiting.a4e.model.CallStack;

public class BapiBean {
	public final static String SCOPE_PARAMETER = "IS_SCOPE"; //$NON-NLS-1$
	public final static String OBJECTS_PARAMETER = "IT_OBJECTS"; //$NON-NLS-1$
	public final static String PATTERNS_PARAMETER = "IT_PATTERNS"; //$NON-NLS-1$
	public final static String CALLSTACK_PARAMETER = "ET_CALLSTACK"; //$NON-NLS-1$
	public final static String FINDINGS_PARAMETER = "ET_FINDINGS"; //$NON-NLS-1$
	public final static String RETURN_PARAMETER = "ET_RETURN"; //$NON-NLS-1$
	public final static String RETCODE_PARAMETER = "EV_RETCODE"; //$NON-NLS-1$
	
	public AlScopeStr scope;
	public ArrayList<AlObjectStr> objects;
	public ArrayList<String> patterns;
	public CallStack callStack;
	public ArrayList<AlFindingStr> findings;
	public ArrayList<BapiRet2> bapiReturn;
	public int retcode;
}
