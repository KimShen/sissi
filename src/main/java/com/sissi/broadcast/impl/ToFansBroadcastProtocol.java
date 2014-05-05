package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.relation.RelationContext;

/**
 * 对JID订阅者所有资源进行XMPP节广播
 * 
 * @author kim 2014年1月15日
 */
public class ToFansBroadcastProtocol extends BaseBroadcastProtocol implements BroadcastProtocol {

	private final RelationContext relationContext;

	public ToFansBroadcastProtocol(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super(jidBuilder, addressing);
		this.relationContext = relationContext;
	}

	@Override
	public ToFansBroadcastProtocol broadcast(JID jid, Protocol protocol) {
		for (JID each : this.relationContext.whoSubscribedMe(jid)) {
			super.addressing.find(each).write(protocol);
		}
		return this;
	}
}
