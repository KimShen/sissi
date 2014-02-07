package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年1月15日
 */
public class ToFansBroadcastProtocol extends ToAnyBroadcastProtocol implements BroadcastProtocol {

	public ToFansBroadcastProtocol(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super(jidBuilder, addressing, relationContext);
	}

	@Override
	public ToFansBroadcastProtocol broadcast(JID jid, Protocol protocol) {
		for (JID each : super.relationContext.whoSubscribedMe(jid)) {
			super.addressing.find(each).write(protocol);
		}
		return this;
	}
}
