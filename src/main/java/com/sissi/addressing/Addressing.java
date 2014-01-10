package com.sissi.addressing;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * Find context for jid
 * @author kim 2013-11-1
 */
public interface Addressing {

	public Addressing join(JIDContext context);

	public Addressing leave(JID jid);

	public Addressing leave(JIDContext context);

	/**
	 * Update priority for context
	 * 
	 * @param context
	 * @return
	 */
	public Addressing priority(JIDContext context);

	/**
	 * Update timestamp for context
	 * 
	 * @param context
	 * @return
	 */
	public Addressing activate(JIDContext context);

	/**
	 * Find jids without resource
	 * 
	 * @param jid
	 * @return
	 */
	public JIDContext find(JID jid);

	public JIDContext find(JID jid, Boolean usingResource);

	/**
	 * Find jid with resource
	 * 
	 * @param jid
	 * @return
	 */
	public JIDContext findOne(JID jid);

	/**
	 * Online nums without resource
	 * 
	 * @param jid
	 * @return
	 */
	public Integer others(JID jid);

	public Integer others(JID jid, Boolean usingResource);
}
