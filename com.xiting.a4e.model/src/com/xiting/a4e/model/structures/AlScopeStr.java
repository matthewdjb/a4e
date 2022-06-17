package com.xiting.a4e.model.structures;

public class AlScopeStr {
	public final static String STANDARD = "STANDARD"; //$NON-NLS-1$
	public final static String PARTNER = "PARTNER"; //$NON-NLS-1$
	public final static String CUSTOM = "CUSTOM"; //$NON-NLS-1$
	public final static String UNIVERSAL_DEPTH = "UNIVERSAL_DEPTH"; //$NON-NLS-1$
	public final static String STANDARD_DEPTH = "STANDARD_DEPTH"; //$NON-NLS-1$
	public final static String PARTNER_DEPTH = "PARTNER_DEPTH"; //$NON-NLS-1$
	public final static String CUSTOM_DEPTH = "CUSTOM_DEPTH"; //$NON-NLS-1$
	public final static String PARTNER_PRODUCER = "PARTNER_PRODUCER"; //$NON-NLS-1$
	
   public boolean scanStandardCode;
   public boolean scanPartnerCode;
   public boolean scanCustomCode;
   public int universalDepth;
   public int standardDepth;
   public int partnerDepth;
   public int customDepth;
   public boolean producerNameSpacesOnly;
}
