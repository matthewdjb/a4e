package com.xiting.a4e.model.structures;

import java.util.HashMap;
import java.util.Map;

public class AlAuthCheckStr {
	public final static String LINE = "LINE";
	public final static String SCOPETYPE = "SCOPETYPE";
	public final static String FLAG = "FLAG";
	public final static String OBJECT = "OBJECT";
	public final static String FIELD01 = "FIELD01";
	public final static String VAL01 = "VAL01";
	public final static String FLAG01 = "FLAG01";
	public final static String FIELD02 = "FIELD02";
	public final static String VAL02 = "VAL02";
	public final static String FLAG02 = "FLAG02";
	public final static String FIELD03 = "FIELD03";
	public final static String VAL03 = "VAL03";
	public final static String FLAG03 = "FLAG03";
	public final static String FIELD04 = "FIELD04";
	public final static String VAL04 = "VAL04";
	public final static String FLAG04 = "FLAG04";
	public final static String FIELD05 = "FIELD05";
	public final static String VAL05 = "VAL05";
	public final static String FLAG05 = "FLAG05";
	public final static String FIELD06 = "FIELD06";
	public final static String VAL06 = "VAL06";
	public final static String FLAG06 = "FLAG06";
	public final static String FIELD07 = "FIELD07";
	public final static String VAL07 = "VAL07";
	public final static String FLAG07 = "FLAG07";
	public final static String FIELD08 = "FIELD08";
	public final static String VAL08 = "VAL08";
	public final static String FLAG08 = "FLAG08";
	public final static String FIELD09 = "FIELD09";
	public final static String VAL09 = "VAL09";
	public final static String FLAG09 = "FLAG09";
	public final static String FIELD10 = "FIELD10";
	public final static String VAL10 = "VAL10";
	public final static String FLAG10 = "FLAG10";

	public int line;
	public String scopetype;
	public String flag;
	public String object;
	public String field01;
	public String val01;
	public String flag01;
	public String field02;
	public String val02;
	public String flag02;
	public String field03;
	public String val03;
	public String flag03;
	public String field04;
	public String val04;
	public String flag04;
	public String field05;
	public String val05;
	public String flag05;
	public String field06;
	public String val06;
	public String flag06;
	public String field07;
	public String val07;
	public String flag07;
	public String field08;
	public String val08;
	public String flag08;
	public String field09;
	public String val09;
	public String flag09;
	public String field10;
	public String val10;
	public String flag10;

	public Map<String, String> returnAsMapString() {
		HashMap<String, String> finding = new HashMap<String, String>();
		finding.put(LINE, ((Integer) line).toString());
		finding.put(SCOPETYPE, scopetype);
		finding.put(FLAG, flag);
		finding.put(FIELD01, field01);
		finding.put(VAL01, val01);
		finding.put(FLAG01, flag01);
		finding.put(FIELD02, field02);
		finding.put(VAL02, val02);
		finding.put(FLAG02, flag02);
		finding.put(FIELD03, field03);
		finding.put(VAL03, val03);
		finding.put(FLAG03, flag03);
		finding.put(FIELD04, field04);
		finding.put(VAL04, val04);
		finding.put(FLAG04, flag04);
		finding.put(FIELD05, field05);
		finding.put(VAL05, val05);
		finding.put(FLAG05, flag05);
		finding.put(FIELD06, field06);
		finding.put(VAL06, val06);
		finding.put(FLAG06, flag06);
		finding.put(FIELD07, field07);
		finding.put(VAL07, val07);
		finding.put(FLAG07, flag07);
		finding.put(FIELD08, field08);
		finding.put(VAL08, val08);
		finding.put(FLAG08, flag08);
		finding.put(FIELD09, field09);
		finding.put(VAL09, val09);
		finding.put(FLAG09, flag09);
		finding.put(FIELD10, field10);
		finding.put(VAL10, val10);
		finding.put(FLAG10, flag10);
		return finding;
	}

	public static String getColumnDescription(String columnName) {
		switch (columnName) {
		case LINE:
			return "Line";
		case SCOPETYPE:
			return "Scope";
		case OBJECT:
			return "Object";
		case FIELD01:
		case FIELD02:
		case FIELD03:
		case FIELD04:
		case FIELD05:
		case FIELD06:
		case FIELD07:
		case FIELD08:
		case FIELD09:
		case FIELD10:
			return "Authorization Field";
		case VAL01:
		case VAL02:
		case VAL03:
		case VAL04:
		case VAL05:
		case VAL06:
		case VAL07:
		case VAL08:
		case VAL09:
		case VAL10:
			return "Authorization Value";
		default:
			return "";
		}

	}

}
