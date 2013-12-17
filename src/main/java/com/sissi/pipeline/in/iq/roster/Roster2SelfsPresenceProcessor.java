package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-18
 */
public class Roster2SelfsPresenceProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.getJid(), super.build(Roster.class.cast(protocol).getFirstItem().getJid()), context.getJid(), new Presence().setType(Presence.Type.UNAVAILABLE));
		return true;
	}
}
