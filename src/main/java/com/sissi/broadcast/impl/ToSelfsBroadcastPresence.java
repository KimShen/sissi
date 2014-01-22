package com.sissi.broadcast.impl;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * @author kim 2014年1月15日
 */
public class ToSelfsBroadcastPresence extends ToAnyBroadcastPresence {

	public ToSelfsBroadcastPresence broadcast(JID jid, JID from, Status status) {
		super.getAddressing().find(jid).write(super.getPresenceBuilder().build(from, status));
		return this;
	}
}
