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
public class RosterSet2FansPresenceProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(super.build(Roster.class.cast(protocol).getFirstItem().getJid()), context.getJid().getBare(), new Presence().setType(PresenceType.SUBSCRIBE));
		return true;
	}
}
