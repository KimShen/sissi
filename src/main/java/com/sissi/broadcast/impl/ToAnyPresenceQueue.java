package com.sissi.broadcast.impl;

import com.sissi.broadcast.BroadcastPresence;
import com.sissi.broadcast.PresenceBuilder;
import com.sissi.context.JID;
import com.sissi.context.Status;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年1月10日
 */
abstract class ToAnyPresenceQueue extends ToAnyProtocolQueue implements BroadcastPresence {

	private PresenceBuilder presenceBuilder;

	public ToAnyPresenceQueue() {
		this.presenceBuilder = new DefaultPresenceBuilder();
	}

	public void setPresenceBuilder(PresenceBuilder presenceBuilder) {
		this.presenceBuilder = presenceBuilder;
	}

	protected PresenceBuilder getPresenceBuilder() {
		return this.presenceBuilder;
	}

	abstract public ToAnyPresenceQueue broadcast(JID jid, JID from, JID to, Status status);

	private class DefaultPresenceBuilder implements PresenceBuilder {

		@Override
		public Presence build(JID from, JID to, Status status) {
			return Presence.class == status.getClass() ? Presence.class.cast(status).setFrom(from.getBare()).setTo(to) : this.newOne(from.asStringWithBare(), to.asString(), status);
		}

		private Presence newOne(String from, String to, Status status) {
			return new Presence(from, to, status.getStatusClauses());
		}
	}
}
