package com.xiting.a4e.model.structures.views;

public enum Views {
	AUTH_BASIC(new AuthBasicViewBean()), AUTH_CHECK(new AuthCheckViewBean()), AUTH_MIS(new AuthMisViewBean()),
	AUTH_PROTOCOL(new AuthProtocolViewBean()), STATISTIC(new StatisticViewBean());

	private IBean bean;

	private Views(IBean bean) {
		this.bean = bean;
	}

	public IBean getBean() {
		return this.bean;
	}
}
