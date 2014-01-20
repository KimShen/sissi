package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribed2MasterPresenceProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext target = super.find(super.build(protocol.getTo()));
		JID other = super.build(context.getJid().asString());
		for (String resource : super.resources(context.getJid())) {
			target.write(new Presence(other.setResource(resource), context.getStatus().getStatusClauses()));
		}
		return true;
	}
}
