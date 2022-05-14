package com.xiting.a4e.backend;

import com.sap.conn.jco.JCoException;
import com.xiting.a4e.model.structures.AlObjectStr;

public interface INavigator {
	public void jump_to(AlObjectStr target, int offset) throws JCoException;
}
