package com.xiting.a4e.model.structures;

import java.util.HashMap;
import java.util.Map;

import com.xiting.a4e.model.A4eTexts;

public class AlFindingStr {
	public static final String PATTERN = "PATTERN"; //$NON-NLS-1$
	public static final String FINDID = "FINDID"; //$NON-NLS-1$
	public static final String FINDID_MAIN = "FINDID_MAIN"; //$NON-NLS-1$
	public static final String CODEPOS_LINE = "CODEPOS_LINE"; //$NON-NLS-1$
	public static final String CODEPOS_INCLUDE = "CODEPOS_INCLUDE"; //$NON-NLS-1$
	public static final String OBJECT = "OBJECT"; //$NON-NLS-1$
	public static final String ARBGB = "ARBGB"; //$NON-NLS-1$
	public static final String MSGNR = "MSGNR"; //$NON-NLS-1$
	public static final String MSGTY = "MSGTY"; //$NON-NLS-1$
	public static final String MESSAGE = "MESSAGE"; //$NON-NLS-1$
	public static final String PARAM1 = "PARAM1"; //$NON-NLS-1$
	public static final String PARAM2 = "PARAM2"; //$NON-NLS-1$
	public static final String PARAMLONG = "PARAMLONG"; //$NON-NLS-1$

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
		HashMap<String, String> finding = new HashMap<String, String>();
		finding.put(PATTERN, pattern);
		finding.put(FINDID, findid);
		finding.put(FINDID_MAIN, isFindidMain ? A4eTexts.getString("AlFindingStr_Yes") : ""); //$NON-NLS-1$ //$NON-NLS-2$
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

	public static String getColumnDescription(String columnName) {
		switch (columnName) {
		case PATTERN:
			return A4eTexts.getString("AlFindingStr_Pattern"); //$NON-NLS-1$
		case FINDID:
			return A4eTexts.getString("AlFindingStr_Findid"); //$NON-NLS-1$
		case FINDID_MAIN:
			return A4eTexts.getString("AlFindingStr_FindidMain"); //$NON-NLS-1$
		case CODEPOS_LINE:
			return A4eTexts.getString("AlFindingStr_CodeposLine"); //$NON-NLS-1$
		case CODEPOS_INCLUDE:
			return A4eTexts.getString("AlFindingStr_CodeposInclude"); //$NON-NLS-1$
		case AlObjectStr.NAME:
			return A4eTexts.getString("AlFindingStr_ObjectName"); //$NON-NLS-1$
		case AlObjectStr.TYPE:
			return A4eTexts.getString("AlFindingStr_ObjectType"); //$NON-NLS-1$
		case AlObjectStr.INCLUDE:
			return A4eTexts.getString("AlFindingStr_ObjectInclude"); //$NON-NLS-1$
		case ARBGB:
			return A4eTexts.getString("AlFindingStr_Arbgb"); //$NON-NLS-1$
		case MSGNR:
			return A4eTexts.getString("AlFindingStr_Msgnr"); //$NON-NLS-1$
		case MSGTY:
			return A4eTexts.getString("AlFindingStr_Msgty"); //$NON-NLS-1$
		case MESSAGE:
			return A4eTexts.getString("AlFindingStr_Message"); //$NON-NLS-1$
		case PARAM1:
			return A4eTexts.getString("AlFindingStr_Param1"); //$NON-NLS-1$
		case PARAM2:
			return A4eTexts.getString("AlFindingStr_Param2"); //$NON-NLS-1$
		case PARAMLONG:
			return A4eTexts.getString("AlFindingStr_Paramlong"); //$NON-NLS-1$
		default:
			return ""; //$NON-NLS-1$
		}
	}
}
