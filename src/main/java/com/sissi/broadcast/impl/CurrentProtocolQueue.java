package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.ProtocolQueue;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class CurrentProtocolQueue implements ProtocolQueue {

	protected Addressing addressing;

	public CurrentProtocolQueue(Addressing addressing) {
		super();
		this.addressing = addressing;
	}

	@Override
	public void offer(JID jid, Protocol protocol) {
		for (JIDContext each : this.addressing.find(jid.getBare())) {
			each.write(protocol);
		}
	}
}
