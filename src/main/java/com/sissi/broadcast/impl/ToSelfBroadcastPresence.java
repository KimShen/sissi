package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.Status;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfBroadcastPresence extends ToAnyBroadcastPresence {

	public ToSelfBroadcastPresence(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super(jidBuilder, addressing, relationContext);
	}

	public ToSelfBroadcastPresence broadcast(JID jid, JID from, Status status) {
		super.addressing.findOne(jid, true).write(super.presenceBuilder.build(from, status));
		return this;
	}
}
