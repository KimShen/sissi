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
	public Boolean input(JIDContext context, Protocol protocol) {
		// write presence with current context status to all resources of jid who be subscribed from current context
		JIDContext target = super.find(super.build(protocol.getTo()));
		// deep copy
		JID other = super.build(context.getJid().asStringWithBare());
		for (String resource : super.resources(context.getJid())) {
			target.write(new Presence(other.setResource(resource), super.findOne(other, true).getStatus().getClauses()));
		}
		return true;
	}
}
