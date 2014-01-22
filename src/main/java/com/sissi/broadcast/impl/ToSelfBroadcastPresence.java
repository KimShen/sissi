package com.sissi.broadcast.impl;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfBroadcastPresence extends ToAnyBroadcastPresence {

	public ToSelfBroadcastPresence broadcast(JID jid, JID from, Status status) {
		super.getAddressing().findOne(jid, true).write(super.getPresenceBuilder().build(from, status));
		return this;
	}
}
