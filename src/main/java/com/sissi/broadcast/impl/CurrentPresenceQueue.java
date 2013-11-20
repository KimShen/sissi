package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.PresenceQueue;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-17
 */
public class CurrentPresenceQueue implements PresenceQueue {

	protected Addressing addressing;

	protected PresenceBuilder presenceBuilder;

	public CurrentPresenceQueue(Addressing addressing, PresenceBuilder presenceBuilder) {
		super();
		this.addressing = addressing;
		this.presenceBuilder = presenceBuilder;
	}

	public void offer(JID jid, JID from, JID to, String show, String status, Type type) {
		Presence presence = this.presenceBuilder.build(from.getBare(), to.getBare(), show, status, type);
		for (JIDContext each : this.addressing.find(jid.getBare())) {
			each.write(presence);
		}
	}
}
