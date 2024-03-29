package com.xiting.a4e.model.utils;

public class AbapAdtTypes {
	public enum AbapType {
		CLAS, FUGR, FUNC, INTF, METH, PROG
	}

	final static class AdtType {
		private static final String CLAS_OC = "CLAS/OC"; //$NON-NLS-1$
		private static final String CLAS_OO = "CLAS/OO"; //$NON-NLS-1$
		private static final String FUGR_FF = "FUGR/FF"; //$NON-NLS-1$
		private static final String PROG_P = "PROG/P"; //$NON-NLS-1$
		private static final String INTF_OI = "INTF/OI"; //$NON-NLS-1$
		private static final String FUGR_F = "FUGR/F"; //$NON-NLS-1$
		static final String FUGR_I = "FUGR/I"; //$NON-NLS-1$
	}

	public static AbapType toAbapType(String adtType) {
		switch (adtType) {
		case AdtType.CLAS_OC:
			return AbapType.CLAS;
		case AdtType.CLAS_OO:
			return AbapType.METH;
		case AdtType.FUGR_FF:
			return AbapType.FUNC;
		case AdtType.PROG_P:
			return AbapType.PROG;
		case AdtType.INTF_OI:
			return AbapType.INTF;
		case AdtType.FUGR_F:
		case AdtType.FUGR_I:
			return AbapType.FUGR;
		default:
			return null;
		}
	}

	public static boolean isSupported(String type) {
		return (AbapType.valueOf(type) !=  AbapType.INTF);
	}
}
