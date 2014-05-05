package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * Presence type = unsubscribe时向To广播
 * 
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribe2PresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = protocol.cast(Presence.class).type(PresenceType.UNAVAILABLE);
		for (JID resource : super.resources(super.build(protocol.getTo()))) {
			super.broadcast(context.jid(), presence.setFrom(resource));
		}
		return true;
	}
}
