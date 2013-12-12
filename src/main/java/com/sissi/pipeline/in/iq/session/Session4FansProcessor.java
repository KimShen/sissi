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
			this.fromMyFans(context, super.build(relation));
		}
		return true;
	}

	private void fromMyFans(JIDContext context, JID from) {
		super.offer(context.getJid().getBare(), from, context.getJid(), super.findOne(from).getStatus());
	}
}
