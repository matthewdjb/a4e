package com.xiting.a4e.model.structures;

import java.util.ArrayList;


public class BapiViewsBean {
	public final static String CALLSTACK_PARAMETER = "IT_CALLSTACK"; //$NON-NLS-1$
	public final static String FINDINGS_PARAMETER = "IT_FINDINGS"; //$NON-NLS-1$
	public final static String AUTH_CHECK_SU24_FLAG = "IV_AUTH_CHECK"; //$NON-NLS-1$
	public final static String AUTH_CHECK_FLAG = "IV_AUTH_BASIC"; //$NON-NLS-1$
	public final static String AUTH_MIS_FLAG = "IV_AUTH_MIS"; //$NON-NLS-1$
	public final static String ENHANCEMENTS_FLAG = "IV_ENHANCEMENTS"; //$NON-NLS-1$
	public final static String STATISTICS_FLAG = "IV_STATISTICS"; //$NON-NLS-1$
	public final static String AUTH_CHECK_SU24_PARAMETER = "ET_AUTH_CHECK"; //$NON-NLS-1$
	public final static String AUTH_CHECKS_PARAMETER = "ET_AUTH_BASIC"; //$NON-NLS-1$
	public final static String MISSING_AUTHS_PARAMETER = "ET_AUTH_MIS"; //$NON-NLS-1$
	public final static String ENHANCEMENTS_PARAMETER = "ET_ENHANCEMENTS_BASIC"; //$NON-NLS-1$
	public final static String STATISTICS_PARAMETER = "ET_STATISTICS_BASIC"; //$NON-NLS-1$

	
	public ArrayList<AlCallStackStr> callStack;
	public ArrayList<AlFindingStr> findings;
	public boolean authCheckSu24Flag;
	public boolean authCheckFlag;
	public boolean missingAuthsFlag;
	public boolean enhancementsFlag;
	public boolean statisticsFlag;
	public ArrayList<AlAuthCheckSu24Str> authChecksSu24;
	public ArrayList<AlAuthCheckStr> authChecks;
	public ArrayList<AlMissingAuthStr> missingAuths;
	public ArrayList<AlEnhancementsStr> enhancements;
	public ArrayList<AlStatisticsStr> statistics;
	public int maxAuthCheckFields;
}
