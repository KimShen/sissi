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
public class PresenceRosterUnSubscribe2PresenceSelfsProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID from = super.build(protocol.getTo());
		for (String resource : super.resources(from)) {
			super.broadcast(context.getJid(), new Presence().setFrom(from.setResource(resource)).setType(PresenceType.UNAVAILABLE));
		}
		return true;
	}
}
