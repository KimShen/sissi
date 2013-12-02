package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.PresenceBroadcast;
import com.sissi.context.JID;
import com.sissi.context.MyPresence;
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

	public void offer(JID jid, JID from, JID to, MyPresence presence) {
		super.offer(jid.getBare(), this.presenceBuilder.build(from.getBare(), to.getBare(), presence));
	}

	private class DefaultPresenceBuilder implements PresenceBuilder {

		@Override
		public Presence build(JID from, JID to, MyPresence presence) {
			return Presence.class.isAssignableFrom(presence.getClass()) ? (Presence) Presence.class.cast(presence).setFrom(from.getBare()).setTo(to.getBare()) : this.newOne(from.getBare(), to.getBare(), presence);
		}

		private Presence newOne(JID from, JID to, MyPresence presence) {
			return new Presence(from.getBare(), to.getBare(), presence.getShowText(), presence.getStatusText(), presence.getTypeText());
		}
	}
}
