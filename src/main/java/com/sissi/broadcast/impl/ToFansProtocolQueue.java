package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月15日
 */
public class ToFansProtocolQueue extends ToAnyProtocolQueue implements BroadcastProtocol {

	@Override
	public ToFansProtocolQueue broadcast(JID jid, Protocol protocol) {
		for (String each : super.getRelationContext().whoSubscribedMe(jid)) {
			super.getAddressing().find(super.getJidBuilder().build(each)).write(protocol.setTo(each));
		}
		return this;
	}
}
