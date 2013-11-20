package com.sissi.context;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

<<<<<<< HEAD
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
=======
	public Boolean access(Boolean canAccess);

	public Boolean access();

	public JID jid(JID jid);

	public JID jid();
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3

	public void write(Protocol protocol);
}
