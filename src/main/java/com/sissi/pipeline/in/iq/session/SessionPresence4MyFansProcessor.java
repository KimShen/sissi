package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.AsynProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-29
 */
public class SessionPresence4MyFansProcessor extends AsynProcessor {

	@Override
	public AsynRunnable doInput(JIDContext context, Protocol protocol) {
		return new SessionPresence4MyFans(context, protocol);
	}

	private void fromMyFans(JIDContext context, JID from) {
		JIDContext fromContext = super.addressing.findOne(from);
		super.presenceQueue.offer(context.getJid().getBare(), from.getBare(), context.getJid().getBare(), fromContext.getPresence());
	}

	private class SessionPresence4MyFans extends AsynRunnable {

		public SessionPresence4MyFans(JIDContext context, Protocol protocol) {
			super(context, protocol);
		}

		public void run() {
			for (String relation : SessionPresence4MyFansProcessor.super.relationContext.iSubscribedWho(context.getJid())) {
				SessionPresence4MyFansProcessor.this.fromMyFans(context, SessionPresence4MyFansProcessor.super.jidBuilder.build(relation));
			}
		}
	}
}
