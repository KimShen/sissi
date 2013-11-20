package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-10-29
 */
public class SessionPresence4MyFansProcessor extends UtilProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		for (String relation : super.relationContext.iSubscribedWho(context.getJid())) {
			JID from = super.jidBuilder.build(relation);
			this.fromMyFans(context, from);
		}
		return true;
	}

	private void fromMyFans(JIDContext context, JID from) {
		JIDContext fromContext = super.addressing.findOne(from);
		if (fromContext != null) {
			super.presenceQueue.offer(context.getJid().getBare(), from.getBare(), context.getJid().getBare(), fromContext.getPresence().show(), fromContext.getPresence().status(), Presence.Type.parse(fromContext.getPresence().type()));
		}
	}
}
