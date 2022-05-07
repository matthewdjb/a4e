package com.xiting.a4e.model;


import org.eclipse.jface.viewers.TreePath;

public class PathNodeAbapConvertorData implements IAdt2AbapParser {
	private TreePath path;
	final private static int CLASS_NAME_LENGTH = 30;

	public PathNodeAbapConvertorData(TreePath path) {
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
//		IAdtObjectReference mainObject = Adapters.adapt(topSegment, IAdtObjectReference.class);
//		String name = mainObject.getName();
//		IAdtObjectReference subObject = null;
//		if (mainObject.getType() == "CLAS/OC" && path.getSegmentCount() > 1) {
//			subObject = Adapters.adapt(path.getSegment(1), IAdtObjectReference.class);
//		}
//		if (subObject != null && subObject.getType() == "METHOD") {
////			name = buildMethodObjectName(name, subObject.getName());
//		}
//		return name;
	}

	private String getNameFromObject(String object) {
		String[] parts = object.split(" ");
		String objectName = parts[1];
		String type = parts[3];
		return type == AbapAdtTypes.AdtType.FUGR_I
				? Adt2AbapParserFactory.getGroupFromFunctionInclude(objectName)
				: objectName;
	}

	private String getNameFromSubObect(String topObject) {
		String subObject = path.getSegment(1).toString();
		return (getTypeFromObject(subObject) == AbapAdtTypes.AbapType.METH)
				? buildMethodObjectName(getNameFromObject(topObject), getNameFromObject(subObject))
				: getNameFromObject(topObject);
	}

	private String buildMethodObjectName(String className, String methodName) {
		return String.format("%1$-" + CLASS_NAME_LENGTH + "s", className) + methodName;
	}

	private AbapAdtTypes.AbapType getTypeFromObject(String object) {
		String[] parts = object.split(" ");
		String type = parts[3];
		return AbapAdtTypes.toAbapType(type);
	}

	@Override
	public String getType() {
		String topObject = path.getFirstSegment().toString();
		if (path.getSegmentCount() > 1) {
			String subObject = path.getSegment(1).toString();
			if (getTypeFromObject(subObject) == AbapAdtTypes.AbapType.METH) {
				return "METH";
			}
		}
		return getTypeFromObject(topObject).toString();
	}
//		IAdtObjectReference mainObject = Adapters.adapt(topSegment, IAdtObjectReference.class);
//		String type = mainObject.getType();
//		IAdtObjectReference subObject = null;
//		if (type == "CLAS/OC" && path.getSegmentCount() > 1) {
//			subObject = Adapters.adapt(path.getSegment(1), IAdtObjectReference.class);
//		}
//		if (subObject != null && subObject.getType() == "METHOD") {
//			type = "METH";
//		} else {
////			type = getAbapType(type);
//		}
//		return type;
}
