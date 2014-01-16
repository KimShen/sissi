package com.sissi.broadcast.impl;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfsPresenceQueue extends ToAnyPresenceQueue {

	public ToSelfsPresenceQueue broadcast(JID jid, JID from, JID to, Status status) {
		super.getAddressing().find(jid).write(super.getPresenceBuilder().build(from, to, status));
		return this;
	}
}
