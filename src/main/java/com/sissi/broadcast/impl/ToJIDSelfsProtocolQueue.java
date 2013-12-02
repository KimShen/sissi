package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class ToJIDSelfsProtocolQueue implements ProtocolBraodcast {

	protected final Addressing addressing;

	public ToJIDSelfsProtocolQueue(Addressing addressing) {
		super();
		this.addressing = addressing;
	}

	@Override
	public void offer(JID jid, Protocol protocol) {
		this.addressing.find(jid.getBare()).write(protocol);
	}
}
