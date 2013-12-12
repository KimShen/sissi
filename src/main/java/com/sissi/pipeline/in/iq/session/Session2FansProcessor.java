package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-10-29
 */
public class Session2FansProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.offer(context.getJid().getBare(), new Presence().setFrom(context.getJid().getBare()));
		return true;
	}
}
