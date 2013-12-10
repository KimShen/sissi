package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月10日
 */
public class PresenceRosterUnSubscribeProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.relationContext.update(context.getJid(), super.build(protocol.getTo()), Roster.Subscription.NONE.toString());
		return true;
	}
}
