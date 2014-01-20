package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfProtocolQueue extends ToAnyProtocolQueue implements BroadcastProtocol {

	@Override
	public ToSelfProtocolQueue broadcast(JID jid, Protocol protocol) {
		// find resource of jid and write presnece, if context can not be found it will write all of its resource (MongoAddressing)
		super.getAddressing().findOne(jid, true).write(protocol);
		return this;
	}
}
