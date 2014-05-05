package com.sissi.server.getout.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.server.getout.Getout;
import com.sissi.ucenter.relation.RelationContext;

/**
 * 强制离席MUC房间
 * 
 * @author kim 2013-11-20
 */
public class MucGetout implements Getout {

	private final Input proxy;

	private final RelationContext relationContext;

	public MucGetout(Input proxy, RelationContext relationContext) {
		super();
		this.proxy = proxy;
		this.relationContext = relationContext;
	}

	@Override
	public MucGetout getout(JIDContext context) {
		Presence presence = new Presence();
		for (JID group : this.relationContext.iSubscribedWho(context.jid())) {
			this.proxy.input(context, presence.setTo(group).type(PresenceType.UNAVAILABLE));
		}
		return this;
	}
}
