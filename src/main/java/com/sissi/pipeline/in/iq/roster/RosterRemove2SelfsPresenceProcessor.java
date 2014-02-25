package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年2月8日
 */
public class RosterRemove2SelfsPresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence().setType(PresenceType.UNAVAILABLE);
		for (JID resource : super.resources(super.build(protocol.cast(Roster.class).getFirstItem().getJid()))) {
			super.broadcast(context.jid(), presence.setFrom(resource));
		}
		return false;
	}
}
