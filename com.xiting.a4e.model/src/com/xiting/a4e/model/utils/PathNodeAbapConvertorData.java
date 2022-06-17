package com.xiting.a4e.model.utils;

import org.eclipse.jface.viewers.TreePath;

class PathNodeAbapConvertorData implements IAdt2AbapParser {
	private TreePath path;
	final private static int CLASS_NAME_LENGTH = 30;

	PathNodeAbapConvertorData(TreePath path) {
		this.path = path;
	}

	@Override
	public String getName() {
		String topObject = path.getFirstSegment().toString();
		if (path.getSegmentCount() > 1) {
			return getNameFromSubObect(topObject);
		} else {
			return getNameFromObject(topObject);
		}
	}

	private String getNameFromObject(String object) {
		String[] parts = object.split(" "); //$NON-NLS-1$
		String objectName = parts[1];
		String type = parts[3];
		return type == AbapAdtTypes.AdtType.FUGR_I ? Adt2AbapParserFactory.getGroupFromFunctionInclude(objectName)
				: objectName;
	}

	private String getNameFromSubObect(String topObject) {
		String subObject = path.getSegment(1).toString();
		return (getTypeFromObject(subObject) == AbapAdtTypes.AbapType.METH)
				? buildMethodObjectName(getNameFromObject(topObject), getNameFromObject(subObject))
				: getNameFromObject(topObject);
	}

	private String buildMethodObjectName(String className, String methodName) {
		return String.format("%1$-" + CLASS_NAME_LENGTH + "s", className) + methodName; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private AbapAdtTypes.AbapType getTypeFromObject(String object) {
		String[] parts = object.split(" "); //$NON-NLS-1$
		String type = parts[3];
		return AbapAdtTypes.toAbapType(type);
	}

	@Override
	public String getType() {
		String topObject = path.getFirstSegment().toString();
		if (path.getSegmentCount() > 1) {
			String subObject = path.getSegment(1).toString();
			if (getTypeFromObject(subObject) == AbapAdtTypes.AbapType.METH) {
				return "METH"; //$NON-NLS-1$
			}
		}
		return getTypeFromObject(topObject).toString();
	}

}
