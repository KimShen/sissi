package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-10-29
 */
public class Session4FansProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (String relation : super.iSubscribedWho(context.getJid().getBare())) {
			JID from = super.build(relation);
			for (String resource : super.resources(from)) {
				context.write(new Presence(from.setResource(resource), super.findOne(from, true).getStatus().getStatusClauses()));
			}
		}
		return true;
	}
}
