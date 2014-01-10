package com.sissi.broadcast.impl;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * Broadcast presence to jid with resource 
 * 
 * @author kim 2014年1月10日
 */
public class ToSelfPresenceQueue extends ToAnyPresenceQueue {

	public ToSelfPresenceQueue broadcast(JID jid, JID from, JID to, Status status) {
		super.getAddressing().find(jid, true).write(super.getPresenceBuilder().build(from, to, status));
		return this;
	}
}
