package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * Presence type = unsubscribed时向To广播
 * 
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribed2PresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		Presence presence = protocol.cast(Presence.class).type(PresenceType.UNAVAILABLE);
		for (JID resource : super.resources(context.jid())) {
			super.broadcast(to, presence.setFrom(resource));
		}
		return true;
	}
}
