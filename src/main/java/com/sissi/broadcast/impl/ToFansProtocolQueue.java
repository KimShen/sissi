package com.sissi.broadcast.impl;

import com.sissi.broadcast.BraodcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class ToFansProtocolQueue extends ToAnyProtocolQueue implements BraodcastProtocol {

	@Override
	public ToFansProtocolQueue broadcast(JID jid, Protocol protocol) {
		for (String each : super.getRelationContext().whoSubscribedMe(jid)) {
			super.getAddressing().find(super.getJidBuilder().build(each)).write(protocol.setTo(each));
		}
		return this;
	}
}
