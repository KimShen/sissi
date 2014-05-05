package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class PresenceBroadcastProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.jid(), protocol.setFrom(context.jid()));
		return true;
	}
}
