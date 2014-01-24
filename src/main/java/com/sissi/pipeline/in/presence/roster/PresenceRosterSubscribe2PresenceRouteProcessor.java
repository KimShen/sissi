package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月23日
 */
public class PresenceRosterSubscribe2PresenceRouteProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		// must using bare jid
		super.broadcast(super.build(protocol.getTo()), context.getJid().getBare(), Presence.class.cast(protocol).setType(PresenceType.SUBSCRIBE));
		return true;
	}
}
