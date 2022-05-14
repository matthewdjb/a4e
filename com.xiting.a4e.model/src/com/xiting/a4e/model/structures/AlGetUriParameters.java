package com.xiting.a4e.model.structures;

public class AlGetUriParameters {
	public final static String NAME_PARAMETER = "IV_NAME";
	public final static String OBJECT_TYPE_PARAMETER = "IV_OBJECT_TYPE";
	public final static String SUBTYPE_PARAMETER = "IV_SUBTYPE";

	public static final String URI_PARAMETER = "EV_URI";
	public final static String RETURN_PARAMETER = "ES_RETURN";

	public final static String NAME = "NAME";
	public final static String OBJECT_TYPE = "OBJECT_TYPE";
	public final static String SUBTYPE = "SUBTYPE";

	public String name;
	public String object_type;
	public String subtype;

	public String uri;
	public BapiRet2 bapiReturn;

	private final static String THIRTY_BLANKS = "                              ";

	public AlGetUriParameters(AlObjectStr target) {
		convertAlObjectStr(target);
	}

	private void convertAlObjectStr(AlObjectStr target) {
		switch (target.type) {
		case "METH" : name = getClassMethodName(target.name);
		              object_type = "CLAS";
		              subtype = "OO";
		              break;
		default: name = target.include;
		         object_type = "PROG";
		         subtype = "I";
		}
	}

	private String getClassMethodName(String targetName) {
		String[] components = targetName.split("=>", 2);
		if (components.length == 2) {
			StringBuilder className = new StringBuilder();
			className.append(components[0]);
			className.append(THIRTY_BLANKS);
			return (className.toString()).substring(0, 30) + components[1];
		}
		return targetName;
	}
}
