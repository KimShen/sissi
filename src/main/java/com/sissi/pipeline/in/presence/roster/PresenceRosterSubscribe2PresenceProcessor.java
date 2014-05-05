package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * Presence type = subscribe时向To广播
 * 
 * @author kim 2014年1月23日
 */
public class PresenceRosterSubscribe2PresenceProcessor extends ProxyProcessor {

	/*
	 * Must using JID.bare
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(super.build(protocol.getTo()), context.jid().bare(), protocol.cast(Presence.class).type(PresenceType.SUBSCRIBE));
		return true;
	}
}
