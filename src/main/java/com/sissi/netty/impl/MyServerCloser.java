package com.sissi.netty.impl;

import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JIDContext;
import com.sissi.netty.ServerCloser;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-20
 */
public class MyServerCloser implements ServerCloser {

	private ProtocolBraodcast protocolBraodcast;

	public MyServerCloser(ProtocolBraodcast protocolBraodcast) {
		super();
		this.protocolBraodcast = protocolBraodcast;
	}

	@Override
	public void close(JIDContext context) {
		context.getPresence().clear();
		this.protocolBraodcast.offer(context.getJid().getBare(), new Presence().setFrom(context.getJid().getBare()).setType(Type.UNAVAILABLE));
	}
}
