package com.sissi.context;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
public interface JIDContext {

	public Boolean access(Boolean canAccess);

	public Boolean access();

	public JID jid(JID jid);

	public JID jid();

	public void write(Protocol protocol);
}
