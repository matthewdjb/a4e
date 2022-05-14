package com.xiting.a4e.model.structures;

public class AlScopeStr {
	public final static String STANDARD = "STANDARD";
	public final static String PARTNER = "PARTNER";
	public final static String CUSTOM = "CUSTOM";
	public final static String UNIVERSAL_DEPTH = "UNIVERSAL_DEPTH";
	public final static String STANDARD_DEPTH = "STANDARD_DEPTH";
	public final static String PARTNER_DEPTH = "PARTNER_DEPTH";
	public final static String CUSTOM_DEPTH = "CUSTOM_DEPTH";
	public final static String PARTNER_PRODUCER = "PARTNER_PRODUCER";
	
   public boolean scanStandardCode;
   public boolean scanPartnerCode;
   public boolean scanCustomCode;
   public int universalDepth;
   public int standardDepth;
   public int partnerDepth;
   public int customDepth;
   public boolean isPartnerProducer;
}
