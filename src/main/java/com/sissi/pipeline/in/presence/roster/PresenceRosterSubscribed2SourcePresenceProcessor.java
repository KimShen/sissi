package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribed2SourcePresenceProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID source = super.build(protocol.getTo());
		super.presenceQueue.offer(source, context.getJid(), source, context.getOnlineStatus().asType(Presence.Type.ONLINE.toString()));
		return true;
	}
}
