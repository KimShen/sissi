package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastPresence;
import com.sissi.broadcast.PresenceBuilder;
import com.sissi.context.JID;
import com.sissi.context.Status;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年1月10日
 */
abstract class ToAnyBroadcastPresence extends ToAnyBroadcastProtocol implements BroadcastPresence {

	private PresenceBuilder presenceBuilder;

	public ToAnyBroadcastPresence() {
		this.presenceBuilder = new DefaultPresenceBuilder();
	}

	public void setPresenceBuilder(PresenceBuilder presenceBuilder) {
		this.presenceBuilder = presenceBuilder;
	}

	protected PresenceBuilder getPresenceBuilder() {
		return this.presenceBuilder;
	}

	abstract public ToAnyBroadcastPresence broadcast(JID jid, JID from, Status status);

	private class DefaultPresenceBuilder implements PresenceBuilder {

		@Override
		public Presence build(JID from, Status status) {
			return Presence.class == status.getClass() ? Presence.class.cast(status).setFrom(from) : this.newOne(from, status);
		}

		private Presence newOne(JID from, Status status) {
			return new Presence(from, status.getClauses());
		}
	}
}
