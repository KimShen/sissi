package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * Broadcast protocol to jid with resource
 * 
 * @author kim 2013-11-18
 */
public class ToSelfProtocolQueue extends ToAnyProtocolQueue implements BroadcastProtocol {

	@Override
	public ToSelfProtocolQueue broadcast(JID jid, Protocol protocol) {
		super.getAddressing().find(jid, true).write(protocol.setTo(jid));
		return this;
	}
}
