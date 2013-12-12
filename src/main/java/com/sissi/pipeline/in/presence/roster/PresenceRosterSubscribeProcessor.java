package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribeProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.offer(super.build(protocol.getTo()), context.getJid(), super.build(protocol.getTo()), Presence.class.cast(protocol).setFrom(context.getJid()).setType(Type.SUBSCRIBE));
		return false;
	}
}
