package com.xiting.a4e.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.xiting.a4e.model.structures.AlCallStackStr;
import com.xiting.a4e.model.structures.AlFindingStr;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.CallStackStructure;
import com.xiting.a4e.model.structures.NavigationTarget;

public class CallStack {

	private static final String MESSAGE = "Message"; //$NON-NLS-1$
	private static final String FINDING = "Finding"; //$NON-NLS-1$
	private CallStackStructure topNode = null;

	private HashMap<String, ArrayList<CallStackStructure>> childMap = new HashMap<String, ArrayList<CallStackStructure>>();
	private HashMap<String, AlObjectStr> objectMap = new HashMap<String, AlObjectStr>();
	private ArrayList<AlCallStackStr> entries = new ArrayList<AlCallStackStr>();
	private HashMap<CallStackStructure, Boolean> parentsChildrenCleanup = new HashMap<CallStackStructure, Boolean>();
	private boolean allCleanedUp;

	public void add(AlCallStackStr entry) {
		entries.add(entry);
	}

	public void buildTree() {
		removeDuplicatesFromEntries();
		CallStackStructure parent = null;
		CallStackStructure child = null;
		for (AlCallStackStr entry : entries) {
			if (topNode == null) {
				parent = getTopNode(entry.called);
			} else {
				if (parent.object != getUniqueObject(entry.caller)) {
					parent = getNewParent(parent, child, entry.caller);
				}
				child = addChild(parent, entry.called);
			}
		}
		entries = null;
	}

	private CallStackStructure addChild(CallStackStructure parent, AlObjectStr called) {
		CallStackStructure child;
		child = new CallStackStructure(getUniqueObject(called));
		parentsChildrenCleanup.put(child, false);
		getDescriptionAndIcon(child);
		child.target = new NavigationTarget(child.object);
		child.parent = parent;
		ArrayList<CallStackStructure> childMapEntry = childMap.get(child.toString());
		if (childMapEntry == null) {
			childMapEntry = new ArrayList<CallStackStructure>();
			childMap.put(child.toString(), childMapEntry);
		}
		childMapEntry.add(child);
		parent.children.add(child);
		return child;
	}

	private CallStackStructure getNewParent(CallStackStructure parent, CallStackStructure child, AlObjectStr caller) {
		parent = getMatchingCaller(getUniqueObject(caller), parent);
		if (parent == null)
			parent = child;
		return parent;
	}

	private CallStackStructure getTopNode(AlObjectStr called) {
		topNode = new CallStackStructure(getUniqueObject(called));
		parentsChildrenCleanup.put(topNode, false);
		getDescriptionAndIcon(topNode);
		topNode.target = new NavigationTarget(topNode.object);
		return topNode;
	}

	private void removeDuplicatesFromEntries() {
		HashMap<String, Boolean> alreadyRead = new HashMap<String, Boolean>();
		ArrayList<AlCallStackStr> newEntries = new ArrayList<AlCallStackStr>();
		for (AlCallStackStr entry : entries) {
			if (alreadyRead.get(entry.toString()) == null) {
				newEntries.add(entry);
				alreadyRead.put(entry.toString(), true);
			}
		}
		entries = newEntries;
	}

	private CallStackStructure getMatchingCaller(AlObjectStr object, CallStackStructure parent) {
		if (parent == null) {
			return null;
		}
		for (CallStackStructure child : parent.children)
			if (child.object == object)
				return child;
		return getMatchingCaller(object, parent.parent);
	}

	private AlObjectStr getUniqueObject(AlObjectStr object) {
		AlObjectStr uniqueObject = objectMap.get(object.toString());
		if (uniqueObject == null) {
			objectMap.put(object.toString(), object);
			uniqueObject = object;
		}
		return uniqueObject;
	}

	private class Finding {
		AlObjectStr object;
		int codepos;
		String pattern;
		String message;

		Finding(AlObjectStr object, int codepos, String pattern, String message, boolean isFindidMain) {
			this.object = getUniqueObject(object);
			this.codepos = codepos;
			this.pattern = pattern;
			this.message = message;
		}

		@Override
		public String toString() {
			return object.toString() + pattern + message;
		}
	}

	private class Message {
		String message;
		int codepos;

		Message(String message, int codepos) {
			this.message = message;
			this.codepos = codepos;
		}
	}

	public void addFindings(ArrayList<AlFindingStr> alFindings) {
		ArrayList<Finding> findings = sortFindings(alFindings);
		String previousPattern = ""; //$NON-NLS-1$
		String previousObject = ""; //$NON-NLS-1$
		ArrayList<Message> messages = null;
		CallStackStructure findingChild = null;
		for (Iterator<Finding> iterator = findings.iterator(); iterator.hasNext();) {
			Finding finding = iterator.next();
			if (newPattern(previousPattern, previousObject, finding)) {
				if (findingChild != null) {
					addFindingToTree(messages, findingChild, previousObject);
				}
				findingChild = createNewFindingChild(finding);
				messages = new ArrayList<Message>();
			}
			messages.add(new Message(finding.message, finding.codepos));
			previousPattern = finding.pattern;
			previousObject = finding.object.toString();
			if (!iterator.hasNext())
				addFindingToTree(messages, findingChild, previousObject);
		}
	}

	private CallStackStructure createNewFindingChild(Finding finding) {
		CallStackStructure findingChild;
		findingChild = new CallStackStructure(finding.object.clone());
		parentsChildrenCleanup.put(findingChild, false);
		findingChild.target = new NavigationTarget(finding.object, finding.codepos);
		findingChild.object.type = FINDING;
		findingChild.description = finding.pattern;
		return findingChild;
	}

	private void addFindingToTree(ArrayList<Message> messages, CallStackStructure findingChild, String objectKey) {
		getUniqueObject(findingChild.object);
		ArrayList<CallStackStructure> matchingChildren = childMap.get(objectKey);
		if (matchingChildren != null) {
			addFindingToMatchingChildren(findingChild, matchingChildren);
			addMessagesToTree(messages, findingChild);
		}
	}

	private void addFindingToMatchingChildren(CallStackStructure findingChild,
			ArrayList<CallStackStructure> matchingChildren) {
		for (CallStackStructure child : matchingChildren) {
			ArrayList<CallStackStructure> children = child.children;
			children.add(findingChild);
		}
	}

	private void addMessagesToTree(ArrayList<Message> messages, CallStackStructure findingChild) {
		for (Message message : messages) {
			CallStackStructure messageChild = new CallStackStructure();
			messageChild.object = getUniqueObject(findingChild.object.clone());
			messageChild.object.type = MESSAGE;
			messageChild.description = message.message;
			messageChild.parent = findingChild;
			messageChild.target = new NavigationTarget(findingChild.object);
			messageChild.target.codepos = message.codepos;
			findingChild.children.add(messageChild);
		}
	}

	private ArrayList<Finding> sortFindings(ArrayList<AlFindingStr> alFindings) {
		ArrayList<Finding> findings = new ArrayList<Finding>();
		for (AlFindingStr alFinding : alFindings) {
			Finding finding = new Finding(alFinding.object, alFinding.codeposLine, alFinding.pattern, alFinding.message,
					alFinding.isFindidMain);
			findings.add(finding);
		}
		findings.sort((pattern1, pattern2) -> (pattern1.toString().compareTo(pattern2.toString())));
		return findings;
	}

	private boolean newPattern(String previousPattern, String previousObject, Finding finding) {
		return !finding.pattern.equals(previousPattern) || !previousObject.equals(finding.object.toString());
	}

	private void getDescriptionAndIcon(CallStackStructure node) {
		String description = ""; //$NON-NLS-1$
		switch (node.object.type) {
		case "CLAS": //$NON-NLS-1$
			description = "Class" + " " + node.object.name; //$NON-NLS-1$ //$NON-NLS-2$ 
			break;
		case "DYNP": //$NON-NLS-1$
			description = A4eTexts.getString("CallStack_Screen1InInclude2"); //$NON-NLS-1$
			description = description.replaceAll("<1>", node.object.name).replaceAll("<2>", node.object.include); //$NON-NLS-1$ //$NON-NLS-2$ 
			break;
		case "METH": //$NON-NLS-1$
			description = A4eTexts.getString("CallStack_Method1InClass2"); //$NON-NLS-1$
			String method = getMethod(node.object.name);
			String clazz = getClass(node.object.name);
			description = description.replaceAll("<1>", method).replaceAll("<2>", clazz); //$NON-NLS-1$ //$NON-NLS-2$ 
			break;
		case "FUNC": //$NON-NLS-1$
			description = A4eTexts.getString("CallStack_Function") + " " + node.object.name; //$NON-NLS-1$ //$NON-NLS-2$
			break;
		case "YMOD": //$NON-NLS-1$
			description = A4eTexts.getString("CallStack_DynproModule") + " " + node.object.name; //$NON-NLS-1$ //$NON-NLS-2$
			break;
		case "YEVT": //$NON-NLS-1$
			description = A4eTexts.getString("CallStack_ProgramEvent") + " " + node.object.name; //$NON-NLS-1$ //$NON-NLS-2$
			break;
		case "YEVI": //$NON-NLS-1$
			description = A4eTexts.getString("CallStack_ProgramEvent1Implementation2"); //$NON-NLS-1$
			String event = getEvent(node.object.name);
			String implementation = getImplementation(node.object.name);
			description = description.replaceAll("<1>", event).replaceAll("<2>", implementation); //$NON-NLS-1$ //$NON-NLS-2$
			break;
		case "YFRM": //$NON-NLS-1$
			description = A4eTexts.getString("CallStack_ProgramForm") + " " + node.object.name; //$NON-NLS-1$ //$NON-NLS-2$
			break;
		default:
			description = node.object.name;
		}
		node.description = description;
	}

	private String getImplementation(String name) {
		String implementation = getEventComponent(name, 1);
		String[] components = implementation.split("<--"); //$NON-NLS-1$
		if (components.length != 2)
			return implementation;
		return components[0];
	}

	private String getEvent(String name) {
		return getEventComponent(name, 0);
	}

	private String getEventComponent(String name, int component) {
		String[] components = name.split("@"); //$NON-NLS-1$
		if (components.length != 2)
			return name;
		return components[component];
	}

	private String getClass(String name) {
		return getClassComponent(name, 0);
	}

	private String getClassComponent(String name, int component) {
		String[] components = name.split("=>"); //$NON-NLS-1$
		if (components.length != 2)
			return name;
		return components[component];
	}

	private String getMethod(String name) {
		return getClassComponent(name, 1);
	}

	public boolean isEmpty() {
		return topNode == null;
	}

	public ArrayList<CallStackStructure> getChildren(CallStackStructure parent) {
		return parent.children;
	}

	public CallStackStructure getParent(Object element) {
		CallStackStructure child = (CallStackStructure) element;
		return child.parent;
	}

	public boolean hasChildren(CallStackStructure parent) {
		if (!allCleanedUp)
			cleanUpParentsChildren();
		ArrayList<CallStackStructure> children = parent.children;
		return children != null && !children.isEmpty();
	}

	private void cleanUpParentsChildren() {
		boolean somethingRemoved = false;
		for (Entry<CallStackStructure, Boolean> parent : parentsChildrenCleanup.entrySet()) {
			HashMap<CallStackStructure, Boolean> readAlready = new HashMap<CallStackStructure, Boolean>();
			ArrayList<CallStackStructure> childrenToRemove = new ArrayList<CallStackStructure>();
			for (CallStackStructure child : parent.getKey().children)
				if (!child.object.type.equals(MESSAGE) && child.children.isEmpty())
					childrenToRemove.add(child);
				else if (readAlready.get(child) != null)
					childrenToRemove.add(child);
				else
					readAlready.put(child, true);
			if (!childrenToRemove.isEmpty()) {
				somethingRemoved = true;
				parent.getKey().children.removeAll(childrenToRemove);
				parent.getKey().children
						.sort((sibling1, sibling2) -> sibling1.toString().compareTo(sibling2.toString()));
			}
			parentsChildrenCleanup.replace(parent.getKey(), true);
		}
		allCleanedUp = !somethingRemoved;
	}

	public ArrayList<CallStackStructure> getTopNodeChildren() {
		cleanUpParentsChildren();
		ArrayList<CallStackStructure> topNodes = new ArrayList<CallStackStructure>();
		topNodes.add(topNode);
		return topNodes;
	}

}
