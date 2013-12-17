package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class ToSelfsProtocolQueue implements ProtocolBraodcast {

	protected final Addressing addressing;

	public ToSelfsProtocolQueue(Addressing addressing) {
		super();
		this.addressing = addressing;
	}

	@Override
	public ToSelfsProtocolQueue broadcast(JID jid, Protocol protocol) {
		this.addressing.find(jid).write(protocol);
		return this;
	}
}
