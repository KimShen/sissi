package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.AsynProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-29
 */
public class BindJoinProcessor extends AsynProcessor {

	@Override
	public AsynRunnable doInput(JIDContext context, Protocol protocol) {
		return new BindJoin(context);
	}

	private class BindJoin extends AsynRunnable {

		public BindJoin(JIDContext context) {
			super(context);
		}

		@Override
		public void run() {
			BindJoinProcessor.this.addressing.ban(super.context);
			BindJoinProcessor.this.addressing.join(super.context);
			super.context.setBinding(true);
		}
	}
}
