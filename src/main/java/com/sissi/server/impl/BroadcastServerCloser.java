package com.sissi.server.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JIDContext;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.server.ServerCloser;

/**
 * @author kim 2013-11-20
 */
public class BroadcastServerCloser implements ServerCloser {

	private final BroadcastProtocol protocolBraodcast;

	public BroadcastServerCloser(BroadcastProtocol protocolBraodcast) {
		super();
		this.protocolBraodcast = protocolBraodcast;
	}

	@Override
	public BroadcastServerCloser close(JIDContext context) {
		this.protocolBraodcast.broadcast(context.getJid(), new Presence().setFrom(context.getJid()).setType(PresenceType.UNAVAILABLE));
		return this;
	}
}
