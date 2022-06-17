package com.xiting.a4e.ui.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.xiting.a4e.model.CallStack;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.CallStackStructure;

public class CallStackDataProvider implements ITreeContentProvider {

	private CallStack callStack;

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof CallStack) {
			callStack = (CallStack) inputElement;
			ArrayList<CallStackStructure> topNodeChildren = callStack.getTopNodeChildren();
			return ArrayContentProvider.getInstance().getElements(topNodeChildren);
		}
		return ArrayContentProvider.getInstance().getElements(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		CallStackStructure parent = (CallStackStructure) parentElement;
		ArrayList<CallStackStructure> children = callStack.getChildren(parent);
		return ArrayContentProvider.getInstance().getElements(children);
	}

	@Override
	public Object getParent(Object element) {
		AlObjectStr child = (AlObjectStr) element;
		return callStack.getParent(child);
	}

	@Override
	public boolean hasChildren(Object element) {
		CallStackStructure parent = (CallStackStructure) element;
		return callStack.hasChildren(parent);
	}
}
