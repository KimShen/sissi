package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013-11-17
 */
public class PresenceRosterUnsubscribedProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.relationContext.update(super.jidBuilder.build(protocol.getTo()), context.getJid(), Roster.Subscription.NONE.toString());
		return true;
	}
}
