package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-29
 */
public class Session4FansProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (String relation : super.relationContext.iSubscribedWho(context.getJid().getBare())) {
			this.fromMyFans(context, super.jidBuilder.build(relation));
		}
		return true;
	}

	private void fromMyFans(JIDContext context, JID from) {
		JIDContext fromContext = super.addressing.findOne(from);
		super.presenceQueue.offer(context.getJid().getBare(), from.getBare(), context.getJid().getBare(), fromContext.getPresence());
	}
}
