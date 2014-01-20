package com.sissi.broadcast.impl;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfPresenceQueue extends ToAnyPresenceQueue {

	public ToSelfPresenceQueue broadcast(JID jid, JID from, Status status) {
		// find resource of jid and write presnece, if context can not be found it will write all of its resource (MongoAddressing)
		super.getAddressing().findOne(jid, true).write(super.getPresenceBuilder().build(from, status));
		return this;
	}
}
