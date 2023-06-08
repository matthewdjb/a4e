package com.xiting.a4e.model.structures;

import java.util.HashMap;
import java.util.Map;

public class AlAuthCheckSu24Str {
	public static final String SU24_TYPE = "SU24_TYPE";
	public static final String SU24_NAME = "SU24_NAME";
	public static final String SU24_TEXT = "SU24_TEXT";
	public final static String TYPE = "TYPE";
	public final static String NAME = "NAME";
	public final static String INCLUDE = "INCLUDE";
	public static final String XROLES = "XROLES";
	public static final String OBJECT = "OBJECT";
	public static final String FIELD = "FIELD";
	public static final String VALUE = "VALUE";
	public static final String SU24_OBJECT = "SU24_OBJECT";
	public static final String SU24_FIELD = "SU24_FIELD";
	public static final String SU24_LOW = "SU24_LOW";
	public static final String SU24_HIDE = "SU24_HIDE";
	public static final String SU24_JUMP = "SU24_JUMP";
	public static final String SU24_APPEND = "SU24_APPEND";
	public static final String SU24_OBJ_APPEND = "SU24_OBJ_APPEN";
	public static final String SU24_NOCHECK = "SU24_NOCHECK";
	public static final String DEPTH = "DEPTH";
	public static final String LINE = "LINE";
	public static final String SCOPETYPE = "SCOPETYPE";
	public static final String CHKTYPE = "CHKTYPE";

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
	public String su24Hide;
	public String su24Jump;
	public String su24Append;
	public String su24ObjAppend;
	public String su24NoCheck;
	public int depth;
	public int line;
	public String scopeType;
	public AlObjectStr alObject;
	public String chktype;

	public Map<String, String> returnAsMapString() {
		HashMap<String, String> authCheckSu24 = new HashMap<String, String>();
		authCheckSu24.put(CHKTYPE, chktype);
		authCheckSu24.put(DEPTH, ((Integer) depth).toString());
		authCheckSu24.put(FIELD, field);
		authCheckSu24.put(INCLUDE, alObject.include);
		authCheckSu24.put(LINE, ((Integer) line).toString());
		authCheckSu24.put(NAME, alObject.name);
		authCheckSu24.put(OBJECT, object);
		authCheckSu24.put(SCOPETYPE, scopeType);
		authCheckSu24.put(SU24_APPEND, su24Append);
		authCheckSu24.put(SU24_FIELD, su24Field);
		authCheckSu24.put(SU24_HIDE, su24Hide);
		authCheckSu24.put(SU24_JUMP, su24Jump);
		authCheckSu24.put(SU24_LOW, su24Low);
		authCheckSu24.put(SU24_NAME, su24Name);
		authCheckSu24.put(SU24_NOCHECK, su24NoCheck);
		authCheckSu24.put(SU24_OBJ_APPEND, su24ObjAppend);
		authCheckSu24.put(SU24_OBJECT, su24Object);
		authCheckSu24.put(SU24_TEXT, su24Text);
		authCheckSu24.put(SU24_TYPE, su24Type);
		authCheckSu24.put(TYPE, alObject.type);
		authCheckSu24.put(VALUE,  value);
		authCheckSu24.put(XROLES, ((Integer) xroles).toString());
		return authCheckSu24;
	}

	public static String getColumnDescription(String columnName) {
		switch (columnName) {
		case SU24_TYPE:
			return "Type";
		case SU24_NAME:
			return "Name";
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
}
