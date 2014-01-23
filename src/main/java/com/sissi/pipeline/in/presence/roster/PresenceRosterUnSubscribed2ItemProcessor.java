package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribed2ItemProcessor extends PresenceRoster2ItemProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		//TODO: Merge with PresenceRosterSubscribed2ItemProcessor
		JID source = super.build(protocol.getTo());
		super.broadcast(source, super.prepare(source, context.getJid()));
		return true;
	}
}
