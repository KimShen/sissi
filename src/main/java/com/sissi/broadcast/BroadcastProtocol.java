package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public interface BroadcastProtocol {

	/**
	 * Broadcast protocol 2 jid selfs or fans
	 * @param jid
	 * @param protocol
	 * @return
	 */
	public BroadcastProtocol broadcast(JID jid, Protocol protocol);
}
