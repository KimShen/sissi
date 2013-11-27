package com.sissi.context;

import com.sissi.protocol.Node;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	public JIDContext setAuth(Boolean canAccess);
	
	public JIDContext setBinding(Boolean isBinding);

	public JIDContext setJid(JID jid);

	public JID getJid();

	public JIDContextPresence getPresence();

	public Boolean isAuth();
	
	public Boolean isBinding();

	public Boolean isLogining();

	public Boolean close();

	public void write(Node node);
}
