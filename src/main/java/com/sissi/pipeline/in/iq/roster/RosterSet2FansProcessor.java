package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-10-31
 */
public class RosterSet2FansProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		// From must using bare jid
		super.broadcast(super.build(protocol.cast(Roster.class).getFirstItem().getJid()), context.jid().bare(), new Presence().setType(PresenceType.SUBSCRIBE));
		return true;
	}
}
