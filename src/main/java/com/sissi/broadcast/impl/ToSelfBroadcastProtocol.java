package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfBroadcastProtocol extends ToAnyBroadcastProtocol implements BroadcastProtocol {

	@Override
	public ToSelfBroadcastProtocol broadcast(JID jid, Protocol protocol) {
		super.getAddressing().findOne(jid, true).write(protocol);
		return this;
	}
}
