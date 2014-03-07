package com.sissi.ucenter.muc;

import com.sissi.context.JID;

/**
 * @author kim 2014年2月20日
 */
public interface MucConfig {
	
	public final static String NICK = "NICK";

	public final static String COUNT = "COUNT";

	public final static String PASSWORD = "PASSWORD";

	public final static String ACTIVATE = "ACTIVATE";

	public final static String AFFILIATION = "AFFILIATION";

	public final static String HIDDEN_NATIVE = "HIDDEN_NATIVE";

	public final static String HIDDEN_COMPUTER = "HIDDEN_COMPUTE";

	public boolean allowed(JID jid, String key, Object value);

	public String mapping(String affiliation);
}
