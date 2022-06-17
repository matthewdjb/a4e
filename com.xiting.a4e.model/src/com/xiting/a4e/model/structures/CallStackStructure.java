package com.xiting.a4e.model.structures;

import java.util.ArrayList;

public class CallStackStructure {
	public AlObjectStr object;
	public String description;
	public NavigationTarget target;
	public CallStackStructure parent;
	public ArrayList<CallStackStructure> children = new ArrayList<CallStackStructure>();

	public CallStackStructure() { // does nothing
	}

	public CallStackStructure(AlObjectStr object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return object == null ? "" : object.toString(); //$NON-NLS-1$
	}
}
