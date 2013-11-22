package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-18
 */
public class RosterRemoveAndOfflineProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.presenceQueue.offer(context.getJid().getBare(), super.jidBuilder.build(Roster.class.cast(protocol).getFirstItem().getJid()).getBare(), context.getJid().getBare(), new Presence().setType(Presence.Type.UNAVAILABLE));
		return true;
	}
}
