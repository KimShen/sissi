package com.sissi.ucenter.muc;

import com.sissi.context.JID;

/**
 * @author kim 2014年2月20日
 */
public interface MucConfig {

	public boolean allowed(JID jid, String key, Object value);
	
	public String mapping(String affiliation);
}
