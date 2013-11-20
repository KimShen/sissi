package com.sissi.addressing;

import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * @author kim 2013-11-1
 */
public interface Addressing {

	/**
	 * Ban the context out off server
	 * 
	 * @param context
	 */
	public void ban(JIDContext context);

	public void join(JIDContext context);

	public void leave(JIDContext context);

	/**
	 * Find contexts which belong the jid even contain def resource
	 * 
	 * @param jid
	 * @return
	 */
	public Collection<JIDContext> find(JID jid);

	/**
	 * Find prioritiest context which belong the jid
	 * 
	 * @param jid
	 * @return
	 */
	public JIDContext findOne(JID jid);

	public Boolean isOnline(JID jid);
}
