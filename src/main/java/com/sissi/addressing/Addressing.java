package com.sissi.addressing;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * Find context for jid
 * 
 * @author kim 2013-11-1
 */
public interface Addressing {

	public Addressing join(JIDContext context);

	public Addressing leave(JID jid);

	public Addressing leave(JIDContext context);

	/**
	 * Update priority
	 * 
	 * @param context
	 * @return
	 */
	public Addressing priority(JIDContext context);

	/**
	 * The last activated context will be choosed if priority equaled
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
	 * Find jid without resource
	 * 
	 * @param jid
	 * @return
	 */
	public JIDContext findOne(JID jid);

	public JIDContext findOne(JID jid, Boolean usingResource);

	/**
	 * Online nums without resource
	 * 
	 * @param jid
	 * @return
	 */
	public Integer others(JID jid);

	public Integer others(JID jid, Boolean usingResource);
}
