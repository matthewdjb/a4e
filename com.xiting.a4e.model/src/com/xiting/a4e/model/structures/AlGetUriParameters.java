package com.xiting.a4e.model.structures;

public class AlGetUriParameters {
	public final static String NAME_PARAMETER = "IV_NAME"; //$NON-NLS-1$
	public final static String OBJECT_TYPE_PARAMETER = "IV_OBJECT_TYPE"; //$NON-NLS-1$
	public final static String SUBTYPE_PARAMETER = "IV_SUBTYPE"; //$NON-NLS-1$

	public static final String URI_PARAMETER = "EV_URI"; //$NON-NLS-1$
	public final static String RETURN_PARAMETER = "ES_RETURN"; //$NON-NLS-1$

// TODO Remove unused code found by UCDetector
// 	public final static String NAME = "NAME";
// TODO Remove unused code found by UCDetector
// 	public final static String OBJECT_TYPE = "OBJECT_TYPE";
// TODO Remove unused code found by UCDetector
// 	public final static String SUBTYPE = "SUBTYPE";

	public String name;
	public String object_type;
	public String subtype;

	public String uri;
	public BapiRet2 bapiReturn;

	private final static String THIRTY_BLANKS = "                              "; //$NON-NLS-1$

	public AlGetUriParameters(AlObjectStr target) {
		convertAlObjectStr(target);
	}

	private void convertAlObjectStr(AlObjectStr target) {
		switch (target.type) {
		case "METH" : name = getClassMethodName(target.name); //$NON-NLS-1$
		              object_type = "CLAS"; //$NON-NLS-1$
		              subtype = "OM"; //$NON-NLS-1$
		              break;
		default: name = target.include;
		         object_type = "PROG"; //$NON-NLS-1$
		         subtype = "I"; //$NON-NLS-1$
		}
	}

	private String getClassMethodName(String targetName) {
		String[] components = targetName.split("=>", 2); //$NON-NLS-1$
		if (components.length == 2) {
			StringBuilder className = new StringBuilder();
			className.append(components[0]);
			className.append(THIRTY_BLANKS);
			return (className.toString()).substring(0, 30) + components[1];
		}
		return targetName;
	}
}
