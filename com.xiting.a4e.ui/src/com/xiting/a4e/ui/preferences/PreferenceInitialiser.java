package com.xiting.a4e.ui.preferences;

import java.util.ArrayList;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlFindingStr;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.AlPatternStr;
import com.xiting.a4e.model.structures.AlScopeStr;
import com.xiting.a4e.ui.views.ViewsManager;

public class PreferenceInitialiser extends AbstractPreferenceInitializer {

	public PreferenceInitialiser() {
	}

	@Override
	public void initializeDefaultPreferences() {
		ScopedPreferenceStore preferences = AlchemistController.factory().getPreferenceStore();
		setScopeDefaults(preferences);
		setPatternsDefaults(preferences);
		setFindingsDefaults(preferences);
		setViewDefaults(preferences);
	}

	private void setViewDefaults(ScopedPreferenceStore preferences) {
		ViewsManager.get().setDefaults();
	}

	private void setFindingsDefaults(ScopedPreferenceStore preferences) {
		preferences.setDefault(AlFindingStr.PATTERN, true);
		preferences.setDefault(AlFindingStr.FINDID, true);
		preferences.setDefault(AlFindingStr.FINDID_MAIN, true);
		preferences.setDefault(AlFindingStr.CODEPOS_LINE, true);
		preferences.setDefault(AlFindingStr.CODEPOS_INCLUDE, true);
		preferences.setDefault(AlObjectStr.NAME, true);
		preferences.setDefault(AlObjectStr.TYPE, true);
		preferences.setDefault(AlObjectStr.INCLUDE, true);
		preferences.setDefault(AlFindingStr.ARBGB, true);
		preferences.setDefault(AlFindingStr.MSGNR, true);
		preferences.setDefault(AlFindingStr.MSGTY, true);
		preferences.setDefault(AlFindingStr.MESSAGE, true);
		preferences.setDefault(AlFindingStr.PARAM1, true);
		preferences.setDefault(AlFindingStr.PARAM2, true);
		preferences.setDefault(AlFindingStr.PARAMLONG, true);
	}

	private void setPatternsDefaults(ScopedPreferenceStore preferences) {
		ArrayList<AlPatternStr> patterns = AlchemistController.factory().getPatterns();
		for (AlPatternStr pattern : patterns) {
			preferences.setDefault(pattern.className, true);
		}
	}

	private void setScopeDefaults(ScopedPreferenceStore preferences) {
		preferences.setDefault(AlScopeStr.UNIVERSAL_DEPTH, 3);
		preferences.setDefault(AlScopeStr.CUSTOM_DEPTH, 3);
		preferences.setDefault(AlScopeStr.CUSTOM, true);
		preferences.setDefault(AlScopeStr.PARTNER_DEPTH, 3);
		preferences.setDefault(AlScopeStr.PARTNER, true);
		preferences.setDefault(AlScopeStr.PARTNER_PRODUCER, false);
		preferences.setDefault(AlScopeStr.STANDARD_DEPTH, 3);
		preferences.setDefault(AlScopeStr.STANDARD, true);
	}

}
