package com.xiting.a4e.model;

import org.eclipse.core.resources.IProject;

import com.sap.conn.jco.JCoDestination;
import com.xiting.a4e.model.structures.BapiBean;

public class AlchemistController {
	private static AlchemistController singleton;
	private BapiBean bean;
	private JCoDestination destination;

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

}
