package com.xiting.a4e.model.structures;

public class AlCallStackStr {
	public final static String CONTEXT = "CONTEXT"; //$NON-NLS-1$
	public final static String CALLER = "CALLER"; //$NON-NLS-1$
	public final static String CALLED = "CALLED"; //$NON-NLS-1$

	public AlContextStr context;
	public AlObjectStr caller;
	public AlObjectStr called;

	@Override
	public String toString() {
		return caller.toString() + caller.include + called.toString()
		+ called.include + context.codepos.include + String.valueOf(context.codepos.line);
	}

	public boolean equals(AlContextStr other) {
		return (this.toString().equals(other.toString()));
	}

}
