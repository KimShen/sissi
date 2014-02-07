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
public class ToSelfsBroadcastProtocol extends ToAnyBroadcastProtocol implements BroadcastProtocol {

	public ToSelfsBroadcastProtocol(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super(jidBuilder, addressing, relationContext);
	}

	@Override
	public ToSelfsBroadcastProtocol broadcast(JID jid, Protocol protocol) {
		super.addressing.find(jid).write(protocol);
		return this;
	}
}
