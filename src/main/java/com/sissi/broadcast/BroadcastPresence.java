package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * @author kim 2013-11-17
 */
public interface BroadcastPresence {

	/**
	 * Broadcast presence 2 jid
	 * @param jid
	 * @param from
	 * @param to
	 * @param status
	 * @return
	 */
	public BroadcastPresence broadcast(JID jid, JID from, JID to, Status status);
}
