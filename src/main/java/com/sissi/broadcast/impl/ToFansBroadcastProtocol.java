package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月15日
 */
public class ToFansBroadcastProtocol extends ToAnyBroadcastProtocol implements BroadcastProtocol {

	@Override
	public ToFansBroadcastProtocol broadcast(JID jid, Protocol protocol) {
		for (String each : super.getRelationContext().whoSubscribedMe(jid)) {
			super.getAddressing().find(super.getJidBuilder().build(each)).write(protocol);
		}
		return this;
	}
}
