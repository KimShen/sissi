package com.sissi.netty.impl;

import com.sissi.broadcast.ProtocolQueue;
import com.sissi.context.JIDContext;
import com.sissi.netty.ServerCloser;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-20
 */
public class MyServerCloser implements ServerCloser {

	private ProtocolQueue protocolQueue;

	public MyServerCloser(ProtocolQueue protocolQueue) {
		super();
		this.protocolQueue = protocolQueue;
	}

	@Override
	public void callback(JIDContext context) {
		this.protocolQueue.offer(context.getJid().getBare(), new Presence().setFrom(context.getJid().getBare()).setType(Type.UNAVAILABLE));
	}
}
