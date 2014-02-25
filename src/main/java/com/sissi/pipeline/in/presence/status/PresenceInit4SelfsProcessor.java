package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年1月21日
 */
public class PresenceInit4SelfsProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.presence() ? true : this.writePresence(context);
	}

	private boolean writePresence(JIDContext context) {
		Presence presence = new Presence();
		for (JID resource : super.resources(context.jid())) {
			context.write(presence.setFrom(resource).clauses(super.findOne(resource, true).status().clauses()), true);
		}
		return true;
	}
}
