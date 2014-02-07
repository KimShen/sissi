package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.BroadcastPresence;
import com.sissi.broadcast.PresenceBuilder;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.Status;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年1月10日
 */
abstract class ToAnyBroadcastPresence extends ToAnyBroadcastProtocol implements BroadcastPresence {

	protected PresenceBuilder presenceBuilder;

	protected ToAnyBroadcastPresence(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super(jidBuilder, addressing, relationContext);
		this.presenceBuilder = new DefaultPresenceBuilder();
	}

	public void setPresenceBuilder(PresenceBuilder presenceBuilder) {
		this.presenceBuilder = presenceBuilder;
	}

	private class DefaultPresenceBuilder implements PresenceBuilder {

		@Override
		public Presence build(JID from, Status status) {
			return Presence.class == status.getClass() ? Presence.class.cast(status).setFrom(from) : this.newOne(from, status);
		}

		private Presence newOne(JID from, Status status) {
			return new Presence().setFrom(from).clauses(status.clauses());
		}
	}
}
