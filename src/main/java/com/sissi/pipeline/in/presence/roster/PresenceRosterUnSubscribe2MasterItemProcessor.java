package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribe2MasterItemProcessor extends PresenceRoster2MasterItemProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.getJid(), super.prepare(context.getJid(), super.build(protocol.getTo()), RosterSubscription.NONE));
		return true;
	}
}
