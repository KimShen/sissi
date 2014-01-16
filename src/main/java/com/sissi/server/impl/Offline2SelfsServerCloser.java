package com.sissi.server.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JIDContext;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.server.ServerCloser;

/**
 * @author kim 2014年1月16日
 */
public class Offline2SelfsServerCloser implements ServerCloser {

	private final BroadcastProtocol protocolBraodcast;

	public Offline2SelfsServerCloser(BroadcastProtocol protocolBraodcast) {
		super();
		this.protocolBraodcast = protocolBraodcast;
	}

	@Override
	public Offline2SelfsServerCloser close(JIDContext context) {
		this.protocolBraodcast.broadcast(context.getJid(), new Presence().setFrom(context.getJid()).setTo(context.getJid().getBare()).setType(PresenceType.UNAVAILABLE));
		return this;
	}
}
