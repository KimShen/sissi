package com.sissi.server.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.server.ServerCloser;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2013-11-20
 */
public class GroupServerCloser implements ServerCloser {

	private final Input input;

	private final RelationContext relationContext;

	public GroupServerCloser(Input input, RelationContext relationContext) {
		super();
		this.input = input;
		this.relationContext = relationContext;
	}

	@Override
	public GroupServerCloser close(JIDContext context) {
		Presence presence = new Presence();
		for (JID group : this.relationContext.iSubscribedWho(context.jid())) {
			this.input.input(context, presence.clear().setTo(group.resource(RelationMuc.class.cast(this.relationContext.ourRelation(context.jid(), group)).getName())).setType(PresenceType.UNAVAILABLE));
		}
		return this;
	}
}
