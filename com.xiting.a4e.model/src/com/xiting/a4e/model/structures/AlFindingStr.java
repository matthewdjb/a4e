package com.xiting.a4e.model.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlFindingStr {
	public static final String PATTERN = "PATTERN";
	public static final String FINDID = "FINDID";
	public static final String FINDID_MAIN = "FINDID_MAIN";
	public static final String CODEPOS_LINE = "CODEPOS_LINE";
	public static final String CODEPOS_INCLUDE = "CODEPOS_INCLUDE";
	public static final String OBJECT = "OBJECT";
	public static final String ARBGB = "ARBGB";
	public static final String MSGNR = "MSGNR";
	public static final String MSGTY = "MSGTY";
	public static final String MESSAGE = "MESSAGE";
	public static final String PARAM1 = "PARAM1";
	public static final String PARAM2 = "PARAM2";
	public static final String PARAMLONG = "PARAMLONG";

	public String pattern;
	public String findid;
	public boolean isFindidMain;
	public int codeposLine;
	public String codeposInclude;
	public AlObjectStr object;
	public String arbgb;
	public String msgnr;
	public String msgty;
	public String message;
	public String param1;
	public String param2;
	public String paramlong;
	
	public Map<String, String> returnAsMapString() {
		HashMap<String,String> finding = new HashMap<String,String>();
		finding.put(PATTERN, pattern);
		finding.put(FINDID, findid);
		finding.put(FINDID_MAIN, isFindidMain ? "Yes":"");
		finding.put(CODEPOS_LINE, Integer.toString(codeposLine));
		finding.put(CODEPOS_INCLUDE, codeposInclude);
		finding.put(AlObjectStr.NAME, object.name);
		finding.put(AlObjectStr.TYPE, object.type);
		finding.put(AlObjectStr.INCLUDE, object.include);
		finding.put(ARBGB, arbgb);
		finding.put(MSGNR, msgnr);
		finding.put(MSGTY, msgty);
		finding.put(MESSAGE, message);
		finding.put(PARAM1, param1);
		finding.put(PARAM2, param2);
		finding.put(PARAMLONG, paramlong);
		return finding;
	}
	
	public ArrayList<String> returnAsArrayListString() {
		ArrayList<String> finding = new ArrayList<String>();
		finding.add(pattern);
		finding.add(findid);
		finding.add(isFindidMain ? "Main":"");
		finding.add(Integer.toString(codeposLine));
		finding.add(object.name);
		finding.add(object.type);
		finding.add(object.include);
		finding.add(arbgb);
		finding.add(msgnr);
		finding.add(msgty);
		finding.add(message);
		finding.add(param1);
		finding.add(param2);
		finding.add(paramlong);
		return finding;
	}
}
