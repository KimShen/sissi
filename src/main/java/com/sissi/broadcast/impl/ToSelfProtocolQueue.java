package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class ToSelfProtocolQueue implements ProtocolBraodcast {

	protected final Addressing addressing;

	public ToSelfProtocolQueue(Addressing addressing) {
		super();
		this.addressing = addressing;
	}

	@Override
	public ToSelfProtocolQueue broadcast(JID jid, Protocol protocol) {
		this.addressing.find(jid, true).write(protocol.setTo(jid.asStringWithBare()));
		return this;
	}
}
