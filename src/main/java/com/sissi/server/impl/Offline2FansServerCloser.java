package com.sissi.server.impl;

import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JIDContext;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;
import com.sissi.server.ServerCloser;

/**
 * @author kim 2013-11-20
 */
public class Offline2FansServerCloser implements ServerCloser {

	private final ProtocolBraodcast protocolBraodcast;

	public Offline2FansServerCloser(ProtocolBraodcast protocolBraodcast) {
		super();
		this.protocolBraodcast = protocolBraodcast;
	}

	@Override
	public void close(JIDContext context) {
		this.protocolBraodcast.offer(context.getJid(), new Presence().setFrom(context.getJid().getBare()).setType(Type.UNAVAILABLE));
	}
}
