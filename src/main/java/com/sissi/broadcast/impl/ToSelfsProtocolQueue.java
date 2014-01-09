package com.sissi.broadcast.impl;

import com.sissi.broadcast.BraodcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class ToSelfsProtocolQueue extends ToAnyProtocolQueue implements BraodcastProtocol {

	@Override
	public ToSelfsProtocolQueue broadcast(JID jid, Protocol protocol) {
		super.getAddressing().find(jid).write(protocol.setTo(jid));
		return this;
	}
}
