package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-18
 */
public class MyFansProtocolQueue implements ProtocolBraodcast {

	private Addressing addressing;

	private JIDBuilder jidBuilder;

	private RelationContext relationContext;

	public MyFansProtocolQueue(Addressing addressing, JIDBuilder jidBuilder, RelationContext relationContext) {
		super();
		this.addressing = addressing;
		this.jidBuilder = jidBuilder;
		this.relationContext = relationContext;
	}

	@Override
	public void offer(JID jid, Protocol protocol) {
		for (String each : this.relationContext.whoSubscribedMe(jid.getBare())) {
			this.addressing.find(this.jidBuilder.build(each)).write(protocol);
		}
	}
}
