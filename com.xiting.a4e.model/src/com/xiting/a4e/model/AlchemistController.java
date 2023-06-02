package com.xiting.a4e.model;

import java.util.ArrayList;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.xiting.a4e.model.structures.AlPatternStr;
import com.xiting.a4e.model.structures.BapiBean;

public class AlchemistController {
	public static final Color XITING_COLOUR = new Color(210, 35, 42);
	private static AlchemistController singleton;
	private BapiBean bean;
	private JCoDestination destination;
	private ScopedPreferenceStore preferenceStore;
	private ArrayList<AlPatternStr> patterns = new ArrayList<AlPatternStr>();
	private JCoTable findingsTable;
	private JCoStructure contextStructure;

	private AlchemistController() {
		singleton = this;
		preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, "com.xiting.a4e.ui"); //$NON-NLS-1$
		addPatterns();
	}

	private void addPatterns() {
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_AUTH_CHECK", A4eTexts.getString("AlchemistController_AuthCheck"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_BADI", A4eTexts.getString("AlchemistController_Badi"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_CALL_DIALOG", A4eTexts.getString("AlchemistController_CallDialog"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_CDS", A4eTexts.getString("AlchemistController_CDS"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_CRIT_STMNTS", A4eTexts.getString("AlchemistController_CritStmnts"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_DATASET", A4eTexts.getString("AlchemistController_Dataset"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_DEBUG", A4eTexts.getString("AlchemistController_Debug"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_ENHANCEMENTS", //$NON-NLS-1$
				A4eTexts.getString("AlchemistController_Enhancements"))); //$NON-NLS-1$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_FUNCTION_CALL", //$NON-NLS-1$
				A4eTexts.getString("AlchemistController_FunctionCall"))); //$NON-NLS-1$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_HR_ABAP", A4eTexts.getString("AlchemistController_HrAbap"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_INSTMETH_CALL", //$NON-NLS-1$
				A4eTexts.getString("AlchemistController_InstmethCall"))); //$NON-NLS-1$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_KERNEL_CALL", A4eTexts.getString("AlchemistController_KernelCall"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_LOCMETH_CALL", //$NON-NLS-1$
				A4eTexts.getString("AlchemistController_LocmethCall"))); //$NON-NLS-1$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_LOGDB_CALL", A4eTexts.getString("AlchemistController_LogdbCall"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_MACROS", A4eTexts.getString("AlchemistController_Macros"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_MODULE_CALL", A4eTexts.getString("AlchemistController_ModuleCall"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_PERFORM_CALL", //$NON-NLS-1$
				A4eTexts.getString("AlchemistController_PerformCall"))); //$NON-NLS-1$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_PID", A4eTexts.getString("AlchemistController_Pid"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_SACF", A4eTexts.getString("AlchemistController_Sacf"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_SCREEN_CALL", A4eTexts.getString("AlchemistController_ScreenCall"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_SEARCH", A4eTexts.getString("AlchemistController_Search"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_SQL_STMNT", A4eTexts.getString("AlchemistController_SqlStmnt"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_STATMETH_CALL", //$NON-NLS-1$
				A4eTexts.getString("AlchemistController_StatmethCall"))); //$NON-NLS-1$
		patterns.add(new AlPatternStr("/XITING/AL_PTRN_SUBMIT", A4eTexts.getString("AlchemistController_Submit"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_SYST_FIELDS", A4eTexts.getString("AlchemistController_SystFields"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_TCODE_AUTHC", A4eTexts.getString("AlchemistController_TcodeAuthc"))); //$NON-NLS-1$ //$NON-NLS-2$
		patterns.add(
				new AlPatternStr("/XITING/AL_PTRN_TCODE_CALL", A4eTexts.getString("AlchemistController_TcodeCall"))); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static AlchemistController factory() {
		if (singleton == null)
			return new AlchemistController();
		return singleton;
	}
	
	public static AlchemistController factory_new() {
		singleton =  new AlchemistController();
		return singleton;
	}

	public void setBean(BapiBean bean) {
		this.bean = bean;
	}

	public BapiBean getBean() {
		if (bean == null)
			this.bean = new BapiBean();
		return this.bean;
	}

	public void setDestination(JCoDestination destination) {
		this.destination = destination;
	}

	public JCoDestination getDestination() {
		return destination;
	}

	public ScopedPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

	public ArrayList<AlPatternStr> getPatterns() {
		return patterns;
	}

	public void setFindingsTable(JCoTable findingsTable) {
		this.findingsTable = findingsTable;
		
	}

	public void setContextStructure(JCoStructure contextStructure) {
		this.contextStructure = contextStructure;
	}
	
	public JCoTable getFindingsTable() {
		return findingsTable;
	}
	
	public JCoStructure getContextStructure() {
		return contextStructure;
	}


}
