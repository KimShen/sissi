package com.sissi.pipeline.in.presence.state;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class PresenceStateBroadcastProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.protocolQueue.offer(context.getJid().getBare(), protocol.setFrom(context.getJid().getBare()));
		return true;
	}
}
