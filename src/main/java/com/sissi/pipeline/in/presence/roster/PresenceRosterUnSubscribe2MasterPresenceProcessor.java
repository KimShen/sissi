package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribe2MasterPresenceProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.offer(context.getJid(), super.build(protocol.getTo()), context.getJid(), new Presence().setType(Type.UNAVAILABLE));
		return true;
	}
}
