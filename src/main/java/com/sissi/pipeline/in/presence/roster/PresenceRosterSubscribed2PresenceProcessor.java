package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * Presence type = subscribed时向To广播
 * 
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribed2PresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		Presence presence = protocol.cast(Presence.class);
		for (JID resource : super.resources(context.jid())) {
			super.broadcast(to, presence.setFrom(resource).clauses(super.findOne(resource, true).status().clauses()));
		}
		return true;
	}
}
