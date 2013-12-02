package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.AsynProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-10-29
 */
public class SessionPresence2MyFansProcessor extends AsynProcessor {

	@Override
	public AsynRunnable doInput(JIDContext context, Protocol protocol) {
		return new SessionPresence2MyFans(context, protocol);
	}

	private class SessionPresence2MyFans extends AsynRunnable {

		private SessionPresence2MyFans(JIDContext context, Protocol protocol) {
			super(context, protocol);
		}

		public void run() {
			SessionPresence2MyFansProcessor.this.protocolQueue.offer(super.context.getJid().getBare(), new Presence().setFrom(super.context.getJid().getBare()));
		}
	}
}
