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
			if (super.addressing.isOnline(from)) {
				super.presenceQueue.offer(context.getJid().getBare(), from.getBare(), context.getJid().getBare(), Presence.Type.ONLINE);
			}
		}
		return true;
	}
}
