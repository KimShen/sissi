package com.sissi.broadcast.impl;

import com.sissi.broadcast.PresenceBuilder;
import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * @author kim 2013-11-17
 */
public class ToSelfsPresenceQueue extends ToAnyPresenceQueue {

	public ToSelfsPresenceQueue(PresenceBuilder presenceBuilder) {
		super(presenceBuilder);
	}

	public ToSelfsPresenceQueue() {
		super();
	}

	public ToSelfsPresenceQueue broadcast(JID jid, JID from, JID to, Status status) {
		super.getAddressing().find(jid).write(super.getPresenceBuilder().build(from, to, status));
		return this;
	}
}
