package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfProtocolQueue extends ToAnyProtocolQueue implements BroadcastProtocol {

	@Override
	public ToSelfProtocolQueue broadcast(JID jid, Protocol protocol) {
		super.getAddressing().find(jid, true).write(protocol.setTo(jid));
		return this;
	}
}
