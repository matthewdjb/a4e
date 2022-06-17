package com.xiting.a4e.model.structures;

public class AlObjectStr {
	public static final String TYPE = "TYPE"; //$NON-NLS-1$
	public static final String NAME = "NAME"; //$NON-NLS-1$
	public static final String INCLUDE = "INCLUDE"; //$NON-NLS-1$

	public String type = ""; //$NON-NLS-1$
	public String name = ""; //$NON-NLS-1$
	public String include = ""; //$NON-NLS-1$

	@Override
	public String toString() {
		return type + name;
	}

	public AlObjectStr(String type, String name, String include) {
		this.type = type;
		this.name = name;
		this.include = include;
	}

	public AlObjectStr(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public AlObjectStr() {
	}

	@Override
	public AlObjectStr clone() {
		return new AlObjectStr(type, name, include);
	}
}
