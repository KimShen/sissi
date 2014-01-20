package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribed2ItemProcessor extends PresenceRoster2ItemProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID master = super.build(protocol.getTo());
		super.broadcast(master, super.prepare(master, context.getJid(), RosterSubscription.NONE));
		return true;
	}
}
