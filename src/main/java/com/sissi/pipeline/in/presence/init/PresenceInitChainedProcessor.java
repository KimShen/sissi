package com.sissi.pipeline.in.presence.init;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ChainedProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年5月5日
 */
public class PresenceInitChainedProcessor extends ChainedProcessor {

	public PresenceInitChainedProcessor(List<Input> processors) {
		super(processors);
	}

	public PresenceInitChainedProcessor(boolean next, List<Input> processors) {
		super(next, processors);
	}

	public boolean input(JIDContext context, Protocol protocol) {
		if (!context.onlined()) {
			return super.input(context, protocol);
		}
		return super.next;
	}
}
