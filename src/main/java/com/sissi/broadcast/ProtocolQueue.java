package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public interface ProtocolQueue {

	public void offer(JID jid, Protocol protocol);
}
