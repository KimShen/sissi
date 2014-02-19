package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribed2PresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		Presence presence = Presence.class.cast(protocol);
		for (JID resource : super.resources(context.jid())) {
			super.broadcast(to, presence.clear().setFrom(resource).clauses(super.findOne(resource, true).status().clauses()));
		}
		return true;
	}
}
