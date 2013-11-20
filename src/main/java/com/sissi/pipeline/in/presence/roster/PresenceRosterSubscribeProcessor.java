package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribeProcessor extends UtilProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.setFrom(context.getJid().getBare());
		super.presenceQueue.offer(super.jidBuilder.build(protocol.getTo()), context.getJid(), super.jidBuilder.build(protocol.getTo()), null, null, Presence.Type.SUBSCRIBE);
		return false;
	}
}
