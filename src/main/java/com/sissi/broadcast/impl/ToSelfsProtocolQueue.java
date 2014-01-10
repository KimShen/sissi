package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * Broadcast protocol to jids without resource
 * 
 * @author kim 2013-11-18
 */
public class ToSelfsProtocolQueue extends ToAnyProtocolQueue implements BroadcastProtocol {

	@Override
	public ToSelfsProtocolQueue broadcast(JID jid, Protocol protocol) {
		super.getAddressing().find(jid).write(protocol.setTo(jid));
		return this;
	}
}
