package com.sissi.server.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.server.ServerCloser;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2013-11-20
 */
public class MucServerCloser implements ServerCloser {

	private final Input proxy;

	private final boolean autoUnavailable;

	private final RelationContext relationContext;

	public MucServerCloser(Input proxy, boolean autoUnavailable, RelationContext relationContext) {
		super();
		this.proxy = proxy;
		this.autoUnavailable = autoUnavailable;
		this.relationContext = relationContext;
	}

	@Override
	public MucServerCloser close(JIDContext context) {
		if (this.autoUnavailable) {
			Presence presence = new Presence();
			for (JID group : this.relationContext.iSubscribedWho(context.jid())) {
				this.proxy.input(context, presence.setTo(group).setType(PresenceType.UNAVAILABLE));
			}

		}
		return this;
	}
}
