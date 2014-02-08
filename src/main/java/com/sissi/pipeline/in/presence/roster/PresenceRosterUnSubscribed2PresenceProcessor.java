package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribed2PresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		Presence presence = Presence.class.cast(protocol);
		this.writeUnsubscribed(context, to, presence).writeUnavailable(context, to, presence);
		return true;
	}

	private PresenceRosterUnSubscribed2PresenceProcessor writeUnsubscribed(JIDContext context, JID to, Presence presence) {
		super.broadcast(to, presence.setType(PresenceType.UNSUBSCRIBED).setFrom(context.jid().asStringWithBare()));
		return this;
	}

	protected PresenceRosterUnSubscribed2PresenceProcessor writeUnavailable(JIDContext context, JID to, Presence presence) {
		presence.setType(PresenceType.UNAVAILABLE);
		for (JID resource : super.resources(context.jid())) {
			super.broadcast(to, presence.clear().setFrom(resource));
		}
		return this;
	}
}
