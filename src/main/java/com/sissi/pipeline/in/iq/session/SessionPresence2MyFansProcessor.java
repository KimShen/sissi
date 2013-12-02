package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-10-29
 */
public class SessionPresence2MyFansProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.protocolQueue.offer(context.getJid().getBare(), new Presence().setFrom(context.getJid().getBare()));
		return true;
	}
}
