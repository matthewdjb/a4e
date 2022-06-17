package com.xiting.a4e.model.structures;

public class AlContextStr {
	public final static String SCOPE = "SCOPE"; //$NON-NLS-1$
	public final static String CODEPOS = "CODEPOS"; //$NON-NLS-1$
	public final static String NOT_IN_SCOPE = "NOT_IN_SCOPE"; //$NON-NLS-1$
	public final static String IGNORED = "IGNORED"; //$NON-NLS-1$
	public final static String GENERIC = "GENERIC"; //$NON-NLS-1$

	public String scope;
	public AlCodepos codepos;
	public boolean isNotInScope;
	public boolean isIgnored;
	public boolean isGeneric;
}
