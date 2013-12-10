package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.PresenceBroadcast;
import com.sissi.context.JID;
import com.sissi.context.OnlineStatus;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-17
 */
public class ToJIDSelfsPresenceQueue extends ToJIDSelfsProtocolQueue implements PresenceBroadcast {

	private final PresenceBuilder presenceBuilder;

	public ToJIDSelfsPresenceQueue(Addressing addressing) {
		super(addressing);
		this.presenceBuilder = new DefaultPresenceBuilder();
	}

	public void offer(JID jid, JID from, JID to, OnlineStatus status) {
		super.offer(jid.getBare(), this.presenceBuilder.build(from.getBare(), to.getBare(), status));
	}

	private class DefaultPresenceBuilder implements PresenceBuilder {

		@Override
		public Presence build(JID from, JID to, OnlineStatus status) {
			return Presence.class.isAssignableFrom(status.getClass()) ? (Presence) Presence.class.cast(status).setFrom(from.getBare()).setTo(to.getBare()) : this.newOne(from.getBare(), to.getBare(), status);
		}

		private Presence newOne(JID from, JID to, OnlineStatus status) {
			return new Presence(from.getBare(), to.getBare(), status.getShowAsText(), status.getStatusAsText(), status.getTypeAsText());
		}
	}
}
