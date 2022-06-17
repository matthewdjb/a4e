package com.xiting.a4e.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.xiting.a4e.model.structures.AlCallStackStr;
import com.xiting.a4e.model.structures.AlFindingStr;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.CallStackStructure;

class CallStackTest {

	private CallStack cut;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		cut = new CallStack();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAdd() {
		AlObjectStr top = new AlObjectStr();
		top.type = "****"; //$NON-NLS-1$
		top.name = "****"; //$NON-NLS-1$
		AlObjectStr n1 = makeAlObjectStr("1"); //$NON-NLS-1$
		AlObjectStr n1_1 = makeAlObjectStr("1.1"); //$NON-NLS-1$
		AlObjectStr n1_1_1 = makeAlObjectStr("1.1.1"); //$NON-NLS-1$
		AlObjectStr n1_2 = makeAlObjectStr("1.2"); //$NON-NLS-1$

//		AlObjectStr n1_1_1 = makeAlObjectStr("1.1.1");
//		AlObjectStr n1_1_1_1 = makeAlObjectStr("1.1.1.1");
//		AlObjectStr n1_1_1_2 = makeAlObjectStr("1.1.1.2");
//		AlObjectStr n1_1_1_3 = makeAlObjectStr("1.1.1.3");
//		AlObjectStr n1_1_2 = makeAlObjectStr("1.1.2");
		cut.add(makeCallStackStrEntry(top, n1));
		cut.add(makeCallStackStrEntry(n1, n1_1));
		cut.add(makeCallStackStrEntry(n1, n1_2));
		cut.add(makeCallStackStrEntry(n1_1, n1_1_1));
		cut.add(makeCallStackStrEntry(n1_2, n1_1_1));
//		cut.add(makeCallStackStrEntry(n1_1, n1_1_1));
//		cut.add(makeCallStackStrEntry(n1_1_1, n1_1_1_1));
//		cut.add(makeCallStackStrEntry(n1_1_1, n1_1_1_2));
//		cut.add(makeCallStackStrEntry(n1_1_1, n1_1_1_3));
//		cut.add(makeCallStackStrEntry(n1_1, n1_1_2));
		cut.buildTree();
		ArrayList<AlFindingStr> findings = new ArrayList<AlFindingStr>();
		findings.add(makeFinding("1.1.1")); //$NON-NLS-1$
		cut.addFindings(findings);
		ArrayList<CallStackStructure> topNodes = cut.getTopNodeChildren();
		assertEquals(1, topNodes.size());
		ArrayList<CallStackStructure> level1 = cut.getChildren(topNodes.get(0));
		ArrayList<CallStackStructure> level2 = cut.getChildren(level1.get(0));
//		ArrayList<CallStackStructure> level3 = cut.getChildren(level2.get(0));
		boolean hasChildren = cut.hasChildren(level2.get(0));
		assertEquals(true, hasChildren);
	}

	private AlObjectStr makeAlObjectStr(String name) {
		AlObjectStr object = new AlObjectStr();
		object.type = "FUNC"; //$NON-NLS-1$
		object.name = name;
		return object;
	}

	private AlFindingStr makeFinding(String name) {
		AlFindingStr finding = new AlFindingStr();
		finding.pattern = "A finding"; //$NON-NLS-1$
		finding.object = new AlObjectStr();
		finding.object.type = "FUNC"; //$NON-NLS-1$
		finding.object.name = name;
		finding.message = "Message"; //$NON-NLS-1$
		return finding;
	}

	private AlCallStackStr makeCallStackStrEntry(AlObjectStr caller, AlObjectStr called) {
		AlCallStackStr entry = new AlCallStackStr();
		entry.caller = caller;
		entry.called = called;
		return entry;
	}
}
