package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribe2ItemProcessor extends PresenceRoster2ItemProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.getJid(), super.prepare(context.getJid(), super.build(protocol.getTo())));
		return true;
	}
}
