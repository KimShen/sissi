package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-29
 */
public class Session4FansProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (String relation : super.iSubscribedWho(context.getJid().getBare())) {
			JID from = super.build(relation);
			super.broadcast(context.getJid(), from, context.getJid(), super.findOne(from).getStatus());
		}
		return true;
	}
}
