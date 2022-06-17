package com.xiting.a4e.backend.bapi.inspRunAdhoc;

import java.util.ArrayList;

import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.xiting.a4e.model.AlchemistController;
import com.xiting.a4e.model.structures.AlObjectStr;
import com.xiting.a4e.model.structures.AlPatternStr;
import com.xiting.a4e.model.structures.AlScopeStr;
import com.xiting.a4e.model.structures.BapiBean;

class CallingParameterHandler {
	private ScopedPreferenceStore store;

	CallingParameterHandler() {
		store = AlchemistController.factory().getPreferenceStore();
	}

	public void setScopeParameter(BapiBean bean) {
		bean.scope = new AlScopeStr();
		bean.scope.scanStandardCode = store.getBoolean(AlScopeStr.STANDARD);
		bean.scope.scanPartnerCode = store.getBoolean(AlScopeStr.PARTNER);
		bean.scope.scanCustomCode = store.getBoolean(AlScopeStr.CUSTOM);
		bean.scope.universalDepth = Integer.parseInt(store.getString(AlScopeStr.UNIVERSAL_DEPTH));
		bean.scope.standardDepth = Integer.parseInt(store.getString(AlScopeStr.STANDARD_DEPTH));
		bean.scope.partnerDepth = Integer.parseInt(store.getString(AlScopeStr.PARTNER_DEPTH));
		bean.scope.customDepth = Integer.parseInt(store.getString(AlScopeStr.CUSTOM_DEPTH));
		bean.scope.producerNameSpacesOnly = store.getBoolean(AlScopeStr.PARTNER_PRODUCER);
	}

	void setItems(BapiBean bean, ArrayList<AlObjectStr> items) {
		bean.objects = items;
	}

	public void setPatternsParameter(BapiBean bean) {
		bean.patterns = new ArrayList<>();
		ArrayList<AlPatternStr> patterns = AlchemistController.factory().getPatterns();
		for (AlPatternStr pattern : patterns) {
			if (store.getBoolean(pattern.className))
				bean.patterns.add(pattern.className);
		}
	}
}
