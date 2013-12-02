package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribedAndOnlineProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID master = super.jidBuilder.build(protocol.getTo());
		super.presenceQueue.offer(master, context.getJid(), master, context.getPresence().setTypeText(Presence.Type.ONLINE.toString()));
		return true;
	}
}
