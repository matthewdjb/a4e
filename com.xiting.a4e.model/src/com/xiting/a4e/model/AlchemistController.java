package com.xiting.a4e.model;

import com.xiting.a4e.model.structures.BapiBean;

public class AlchemistController {
	private static AlchemistController singleton;
	private BapiBean bean;

	private AlchemistController() {
		singleton = this;
	}

	public static AlchemistController factory() {
		if (singleton == null)
			new AlchemistController();
		return singleton;
	}

	public void setBean(BapiBean bean) {
		this.bean = bean;
	}
	
	public BapiBean getBean() {
		if (bean == null) this.bean = new BapiBean();
		return this.bean;
	}
}
