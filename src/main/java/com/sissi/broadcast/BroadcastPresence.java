package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * @author kim 2013-11-17
 */
public interface BroadcastPresence {

	public BroadcastPresence broadcast(JID jid, JID from, Status status);
}
