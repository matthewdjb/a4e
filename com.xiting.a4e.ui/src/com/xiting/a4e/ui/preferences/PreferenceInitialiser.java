package com.xiting.a4e.ui.preferences;

import java.util.ArrayList;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlAuthCheckStr;
import com.xiting.a4e.model.structures.AlAuthCheckSu24Str;
import com.xiting.a4e.model.structures.AlFindingStr;
import com.xiting.a4e.model.structures.AlMissingAuthStr;
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
		setAuthCheckDefaults(preferences);
		setAuthSu24CheckDefaults(preferences);
		setMissingAuthsDefaults(preferences);
		setViewDefaults(preferences);
	}

	private void setMissingAuthsDefaults(ScopedPreferenceStore preferences) {
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.CHKTYPE, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.DEPTH, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.FIELD, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.INCLUDE, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.LINE, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.NAME, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.OBJECT, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.SCOPETYPE, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.SU24_FIELD, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.SU24_LOW, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.SU24_NAME, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.SU24_NOCHECK, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.SU24_OBJECT, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.SU24_TEXT, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.SU24_TYPE, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.TYPE, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.VALUE, true);
		preferences.setDefault(AlMissingAuthStr.PREFIX + AlMissingAuthStr.XROLES, true);	
		
	}

	private void setAuthSu24CheckDefaults(ScopedPreferenceStore preferences) {
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.CHKTYPE, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.DEPTH, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.FIELD, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.INCLUDE, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.LINE, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.NAME, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.OBJECT, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.SCOPETYPE, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.SU24_FIELD, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.SU24_LOW, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.SU24_NAME, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.SU24_NOCHECK, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.SU24_OBJECT, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.SU24_TEXT, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.SU24_TYPE, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.TYPE, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.VALUE, true);
		preferences.setDefault(AlAuthCheckSu24Str.PREFIX + AlAuthCheckSu24Str.XROLES, true);		
	}

	private void setAuthCheckDefaults(ScopedPreferenceStore preferences) {
		preferences.setDefault(AlAuthCheckStr.PREFIX + AlAuthCheckStr.LINE,true);
		preferences.setDefault(AlAuthCheckStr.PREFIX + AlAuthCheckStr.SCOPETYPE,true);
		preferences.setDefault(AlAuthCheckStr.PREFIX + AlAuthCheckStr.OBJECT,true);
		preferences.setDefault(AlAuthCheckStr.PREFIX + AlAuthCheckStr.FIELDNN,true);
		preferences.setDefault(AlAuthCheckStr.PREFIX + AlAuthCheckStr.VALNN,true);
		preferences.setDefault(AlAuthCheckStr.PREFIX + AlAuthCheckStr.FLAGNN,true);
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
