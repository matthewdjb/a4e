package com.xiting.a4e.backend;

import com.sap.conn.jco.JCoException;
import com.xiting.a4e.model.structures.NavigationTarget;

public interface INavigator {
	public void jump_to(NavigationTarget target) throws JCoException;
}
