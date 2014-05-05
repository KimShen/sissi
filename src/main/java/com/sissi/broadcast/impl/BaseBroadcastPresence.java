package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.BroadcastPresence;
import com.sissi.broadcast.PresenceBuilder;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.Status;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年1月10日
 */
abstract class BaseBroadcastPresence extends BaseBroadcastProtocol implements BroadcastPresence {

	protected final PresenceBuilder presenceBuilder;

	protected BaseBroadcastPresence(JIDBuilder jidBuilder, Addressing addressing) {
		this(jidBuilder, addressing, new DefaultPresenceBuilder());
	}

	protected BaseBroadcastPresence(JIDBuilder jidBuilder, Addressing addressing, PresenceBuilder presenceBuilder) {
		super(jidBuilder, addressing);
		this.presenceBuilder = presenceBuilder;
	}

	private static class DefaultPresenceBuilder implements PresenceBuilder {

		@Override
		public Presence build(JID from, Status status) {
			return status.getClass() == Presence.class ? Presence.class.cast(status).setFrom(from) : new Presence().setFrom(from).clauses(status.clauses());
		}
	}
}
