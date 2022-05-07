package com.xiting.a4e.model;

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.ui.part.MultiPageEditorPart;

public class Adt2AbapParserFactory {
	public static final IAdt2AbapParser getInstance(Object object) {
		if (object instanceof MultiPageEditorPart) {
			return new Editor2AbapParser((MultiPageEditorPart)object);
		}
		if (object instanceof TreePath) {
			return new PathNodeAbapConvertorData((TreePath) object);
		}
		return null;
	}

	public static boolean inANameSpace(String fileName) {
		return fileName.substring(0, 1) == "/";
	}

	public static String getGroupFromFunctionInclude(String includeName) {
		return inANameSpace(includeName) ? getGroupWithNameSpace(includeName) : getGroupWithoutNameSpace(includeName);
	}

	private static String getGroupWithNameSpace(String objectName) {
		String splitter = objectName.contains("%2F") ? "%2F" : "//";
		String[] parts = objectName.split(splitter);
		String nameSpace = parts[0];
		String name = parts[1].substring(1);
		return "/" + nameSpace + "/" + name;
	}

	private static String getGroupWithoutNameSpace(String objectName) {
		return objectName.substring(1);
	}
}
