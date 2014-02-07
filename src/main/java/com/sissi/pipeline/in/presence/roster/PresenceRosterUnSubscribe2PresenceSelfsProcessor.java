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
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = Presence.class.cast(protocol);
		for (JID resource : super.resources(super.build(protocol.getTo()))) {
			super.broadcast(context.jid(), presence.setFrom(resource).setType(PresenceType.UNAVAILABLE));
		}
		return true;
	}
}
