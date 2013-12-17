  package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.PresenceBroadcast;
import com.sissi.context.JID;
import com.sissi.context.JIDContext.Status;
import com.sissi.context.JIDContext.StatusClauses;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-17
 */
public class ToSelfsPresenceQueue extends ToSelfsProtocolQueue implements PresenceBroadcast {

	private final PresenceBuilder presenceBuilder;

	public ToSelfsPresenceQueue(Addressing addressing) {
		super(addressing);
		this.presenceBuilder = new DefaultPresenceBuilder();
	}

	public ToSelfsPresenceQueue broadcast(JID jid, JID from, JID to, Status status) {
		super.broadcast(jid, this.presenceBuilder.build(from, to, status));
		return this;
	}

	private class DefaultPresenceBuilder implements PresenceBuilder {

		@Override
		public Presence build(JID from, JID to, Status status) {
			return Presence.class.isAssignableFrom(status.getClass()) ? (Presence) Presence.class.cast(status).setFrom(from.asStringWithBare()).setTo(to.asStringWithBare()) : this.newOne(from.asStringWithBare(), to.asStringWithBare(), status);
		}

		private Presence newOne(String from, String to, Status status) {
			StatusClauses clauses = status.getStatus();
			return new Presence(from, to, clauses.find(StatusClauses.KEY_SHOW), clauses.find(StatusClauses.KEY_STATUS), clauses.find(StatusClauses.KEY_TYPE), clauses.find(StatusClauses.KEY_AVATOR));
		}
	}
}
