package com.sissi.broadcast.impl;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * Broadcast presence to jid with resource
 * 
 * @author kim 2014年1月10日
 */
public class ToSelfsPresenceQueue extends ToAnyPresenceQueue {

	public ToSelfsPresenceQueue broadcast(JID jid, JID from, JID to, Status status) {
		super.getAddressing().find(jid).write(super.getPresenceBuilder().build(from, to, status));
		return this;
	}
}
