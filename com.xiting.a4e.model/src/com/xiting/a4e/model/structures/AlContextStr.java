package com.xiting.a4e.model.structures;

public class AlContextStr {
	public final static String SCOPE = "SCOPE";
	public final static String CODEPOS = "CODEPOS";
	public final static String NOT_IN_SCOPE = "NOT_IN_SCOPE";
	public final static String IGNORED = "IGNORED";
	public final static String GENERIC = "GENERIC";

	public String scope;
	public AlCodepos codepos;
	public boolean isNotInScope;
	public boolean isIgnored;
	public boolean isGeneric;
}
