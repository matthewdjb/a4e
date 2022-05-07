package com.xiting.a4e.model;

public class AbapAdtTypes {
	public enum AbapType {
		CLAS, FUGR, FUNC, INTF, METH, PROG
	}

	public final static class AdtType {
		public static final String CLAS_OC = "CLAS/OC";
		public static final String CLAS_OO = "CLAS/OO";
		public static final String FUGR_FF = "FUGR/FF";
		public static final String PROG_P = "PROG/P";
		public static final String INTF_OI = "INTF/OI";
		public static final String FUGR_F = "FUGR/F";
		public static final String FUGR_I = "FUGR/I";
	}

	public enum AdtFileType {
		aclass, asfunc, asprog, asfugr, asfinc, aint;

		public AbapType toAbapType(AdtFileType fileType) {
			switch (fileType) {
			case aclass:
				return AbapType.CLAS;
			case aint:
				return AbapType.INTF;
			case asfinc:
				return AbapType.FUGR;
			case asfugr:
				return AbapType.FUGR;
			case asfunc:
				return AbapType.FUNC;
			case asprog:
				return AbapType.PROG;
			default:
				return null;
			}
		}
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
