package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribeProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.update(context.getJid(), super.build(protocol.getTo()), RosterSubscription.NONE.toString());
		return true;
	}
}
