package com.xiting.a4e.model.structures;

import java.util.ArrayList;


public class BapiViewsBean {
	public final static String CALLSTACK_PARAMETER = "IT_CALLSTACK"; //$NON-NLS-1$
	public final static String FINDINGS_PARAMETER = "IT_FINDINGS"; //$NON-NLS-1$
	public final static String AUTH_CHECK_FLAG = "IV_AUTH_CHECK"; //$NON-NLS-1$
	public final static String AUTH_BASIC_FLAG = "IV_AUTH_BASIC"; //$NON-NLS-1$
	public final static String AUTH_MIS_FLAG = "IV_AUTH_MIS"; //$NON-NLS-1$
	public final static String ENHANCEMENTS_FLAG = "ENHANCEMENTS_BASIC"; //$NON-NLS-1$
	public final static String STATISTICS_FLAG = "STATISTICS_BASIC"; //$NON-NLS-1$
	public final static String AUTH_CHECK_PARAMETER = "ET_AUTH_CHECK"; //$NON-NLS-1$
	public final static String AUTH_BASIC_PARAMETER = "ET_AUTH_BASIC"; //$NON-NLS-1$
	public final static String AUTH_MIS_PARAMETER = "ET_AUTH_MIS"; //$NON-NLS-1$
	public final static String ENHANCEMENTS_PARAMETER = "ET_ENHANCEMENTS_BASIC"; //$NON-NLS-1$
	public final static String STATISTICS_PARAMETER = "ET_STATISTICS_BASIC"; //$NON-NLS-1$

	
	public AlCallStackStr callStack;
	public ArrayList<AlFindingStr> findings;
	public boolean authCheckFlag;
	public boolean authBasicFlag;
	public boolean authMisFlag;
	public boolean enhancementsFlag;
	public boolean statisticsFlag;
	public ArrayList<AlAuthCheckStr> authChecks;
	public ArrayList<AlAuthBasicStr> authBasic;
	public ArrayList<AlAuthMisStr> authMis;
	public ArrayList<AlEnhancementsStr> enhancements;
	public ArrayList<AlStatistics> statistics;
	
}
