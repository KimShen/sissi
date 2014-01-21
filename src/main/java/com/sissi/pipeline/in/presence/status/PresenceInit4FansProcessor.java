package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年1月21日
 */
public class PresenceInit4FansProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isPresence() ? true : this.init4Fans(context);
	}

	private Boolean init4Fans(JIDContext context) {
		for (String relation : super.iSubscribedWho(context.getJid().getBare())) {
			// Get presence from all resources of jid who subscribed me
			JID from = super.build(relation);
			for (String resource : super.resources(from)) {
				context.write(new Presence(from.setResource(resource), super.findOne(from, true).getStatus().getClauses()));
			}
		}
		return true;
	}
}
