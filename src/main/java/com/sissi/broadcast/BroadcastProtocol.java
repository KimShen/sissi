package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * XMPP节广播
 * 
 * @author kim 2013-11-18
 */
public interface BroadcastProtocol {

	/**
	 * @param jid 目标JID
	 * @param protocol XMPP节
	 * @return
	 */
	public BroadcastProtocol broadcast(JID jid, Protocol protocol);
}
