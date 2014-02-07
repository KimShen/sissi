package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.Status;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfsBroadcastPresence extends ToAnyBroadcastPresence {

	protected ToSelfsBroadcastPresence(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super(jidBuilder, addressing, relationContext);
	}

	public ToSelfsBroadcastPresence broadcast(JID jid, JID from, Status status) {
		super.addressing.find(jid).write(super.presenceBuilder.build(from, status));
		return this;
	}
}
