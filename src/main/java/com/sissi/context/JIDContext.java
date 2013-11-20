package com.sissi.context;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	public JIDContext setAuth(Boolean canAccess);

	public JIDContext setJid(JID jid);

	public JID getJid();
	
	public Boolean isAuth();

	/**
	 * JID is logining
	 * @return
	 */
	public Boolean isLogining();
	
	public Boolean close();

	public void write(Protocol protocol);
}
