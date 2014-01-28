package com.sissi.ucenter;

import com.sissi.context.JID;

/**
 * @author kim 2014年1月28日
 */
public interface SignatureContext {

	public SignatureContext signature(JID jid, String signature);

	public String signature(JID jid);
}
