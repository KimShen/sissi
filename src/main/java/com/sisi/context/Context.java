package com.sisi.context;

import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
public interface Context {

	public Boolean access(Boolean canAccess);

	public Boolean access();

	public JID jid(JID jid);

	public JID jid();

	public void write(Protocol protocol);
}
