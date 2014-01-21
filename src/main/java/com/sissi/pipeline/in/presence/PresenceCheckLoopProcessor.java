package com.sissi.pipeline.in.presence;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月17日
 */
public class PresenceCheckLoopProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return !context.getJid().getUser().equals(super.build(protocol.getTo()).getUser()) ? true : false;
	}
}
