package com.sissi.pipeline.in.presence.status;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013年12月25日
 */
public class PresenceStatus2SelfPriorityProcessor implements Input {

	private Addressing addressing;

	public PresenceStatus2SelfPriorityProcessor(Addressing addressing) {
		super();
		this.addressing = addressing;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.addressing.promote(context.setPriority(Presence.class.cast(protocol).getPriority()));
		return true;
	}
}
