package com.sissi.server.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JIDContext;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.server.ServerCloser;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2013-11-20
 */
public class BroadcastServerCloser implements ServerCloser {

	private final BroadcastProtocol protocolBraodcast;

	private final VCardContext vCardContext;

	public BroadcastServerCloser(BroadcastProtocol protocolBraodcast, VCardContext vCardContext) {
		super();
		this.protocolBraodcast = protocolBraodcast;
		this.vCardContext = vCardContext;
	}

	@Override
	public BroadcastServerCloser close(JIDContext context) {
		this.protocolBraodcast.broadcast(context.jid(), new Presence().setFrom(context.jid()).setStatus(this.vCardContext.get(context.jid(), VCardContext.FIELD_SIGNATURE).getValue()).setType(PresenceType.UNAVAILABLE));
		return this;
	}
}
