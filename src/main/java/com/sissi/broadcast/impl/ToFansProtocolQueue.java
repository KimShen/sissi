package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2013-11-18
 */
public class ToFansProtocolQueue implements ProtocolBraodcast {

	private final JIDBuilder jidBuilder;

	private final Addressing addressing;

	private final RelationContext relationContext;

	public ToFansProtocolQueue(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public ToFansProtocolQueue broadcast(JID jid, Protocol protocol) {
		for (String each : this.relationContext.whoSubscribedMe(jid)) {
			JID to = this.jidBuilder.build(each);
			this.addressing.find(to).write(protocol.setTo(to));
		}
		return this;
	}
}
