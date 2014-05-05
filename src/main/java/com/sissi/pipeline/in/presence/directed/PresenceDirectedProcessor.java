package com.sissi.pipeline.in.presence.directed;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * 定向出席
 * 
 * @author kim 2014年1月28日
 */
public class PresenceDirectedProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(super.build(protocol.getTo()), context.jid(), context.status());
		return true;
	}
}
