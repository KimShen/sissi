package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JID;
import com.sissi.context.JID.JIDBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-18
 */
public class ToJIDFansProtocolQueue implements ProtocolBraodcast {

	private final JIDBuilder jidBuilder;

	private final Addressing addressing;

	private final RelationContext relationContext;

	public ToJIDFansProtocolQueue(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public void offer(JID jid, Protocol protocol) {
		for (String each : this.relationContext.whoSubscribedMe(jid.getBare())) {
			this.addressing.find(this.jidBuilder.build(each)).write(protocol);
		}
	}
}
