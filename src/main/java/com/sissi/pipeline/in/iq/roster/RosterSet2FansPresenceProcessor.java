package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-10-31
 */
public class RosterSet2FansPresenceProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID slave = super.build(Roster.class.cast(protocol).getFirstItem().getJid());
		super.presenceQueue.offer(slave, context.getJid(), slave, new Presence().setType(Presence.Type.SUBSCRIBE));
		return true;
	}
}
