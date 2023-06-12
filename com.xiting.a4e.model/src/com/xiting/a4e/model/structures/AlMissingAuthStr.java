package com.xiting.a4e.model.structures;

import java.util.HashMap;
import java.util.Map;

public class AlMissingAuthStr {
	public final static String SU24_TYPE = "SU24_TYPE";
	public final static String SU24_NAME = "SU24_NAME";
	public final static String SU24_TEXT = "SU24_TEXT";
	public final static String XROLES = "XROLES";
	public final static String OBJECT = "OBJECT";
	public final static String FIELD = "FIELD";
	public final static String VALUE = "VALUE";
	public final static String SU24_OBJECT = "SU24_OBJECT";
	public final static String SU24_FIELD = "SU24_FIELD";
	public final static String SU24_LOW = "SU24_LOW";
	public final static String SU24_NOCHECK = "SU24_NOCHECK";
	public final static String DEPTH = "DEPTH";
	public final static String LINE = "LINE";
	public final static String SCOPETYPE = "SCOPETYPE";
	public final static String TYPE = "TYPE";
	public final static String NAME = "NAME";
	public final static String INCLUDE = "INCLUDE";
	public final static String CHKTYPE = "CHKTYPE";

	public final static String PREFIX = "MISSING_AUTHS";

	public String su24Type;
	public String su24Name;
	public String su24Text;
	public int xroles;
	public String object;
	public String field;
	public String value;
	public String su24Object;
	public String su24Field;
	public String su24Low;
	public String su24Nocheck;
	public int depth;
	public int line;
	public String scopetype;
	public AlObjectStr alObject;
	public String chktype;

	public static String getColumnDescription(String columnName) {
		switch (columnName) {
		case SU24_TYPE:
			return "Type";
		case SU24_NAME:
			return "Name";
		case SU24_TEXT:
			return "Text";
		case SU24_OBJECT:
		case OBJECT:
		case INCLUDE:
			return "Object";
		case SU24_FIELD:
		case FIELD:
			return "Authorization Field";
		case SU24_LOW:
			return "Authorization Value";
		case SU24_NOCHECK:
			return "No check";
		case DEPTH:
			return "Depth";
		case LINE:
			return "Line";
		case SCOPETYPE:
			return "Scope";
		case TYPE:
			return "Type";
		case VALUE:
			return "Value";
		default:
			return "";
		}
	}

	public Map<String, String> returnAsMapString() {
		HashMap<String, String> AlMissingAuthStr = new HashMap<String, String>();
		AlMissingAuthStr.put(SU24_NAME, su24Name);
		AlMissingAuthStr.put(SU24_TYPE, su24Type);
		AlMissingAuthStr.put(SU24_TEXT, su24Text);
		AlMissingAuthStr.put(OBJECT, object);
		AlMissingAuthStr.put(FIELD, field);
		AlMissingAuthStr.put(VALUE, value);
		AlMissingAuthStr.put(XROLES, ((Integer) xroles).toString());
		AlMissingAuthStr.put(CHKTYPE, chktype);
		AlMissingAuthStr.put(DEPTH, ((Integer) depth).toString());
		AlMissingAuthStr.put(INCLUDE, alObject.include);
		AlMissingAuthStr.put(LINE, ((Integer) line).toString());
		AlMissingAuthStr.put(NAME, alObject.name);
		AlMissingAuthStr.put(SCOPETYPE, scopetype);
		AlMissingAuthStr.put(SU24_FIELD, su24Field);
		AlMissingAuthStr.put(SU24_LOW, su24Low);
		AlMissingAuthStr.put(SU24_NOCHECK, su24Nocheck);
		AlMissingAuthStr.put(SU24_OBJECT, su24Object);
		AlMissingAuthStr.put(TYPE, alObject.type);
		return AlMissingAuthStr;
	}

}
