package com.xiting.a4e.model.structures;

public class NavigationTarget {
	public AlObjectStr object;
	public int codepos;
	public NavigationTarget(AlObjectStr object,int codepos) {
		this.object = object;
		this.codepos = codepos;
	}
	public NavigationTarget(AlObjectStr object) {
		this.object = object;
		this.codepos = 0;
	}

}
