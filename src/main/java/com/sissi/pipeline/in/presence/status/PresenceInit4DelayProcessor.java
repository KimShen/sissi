package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.offline.DelayElementBox;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月21日
 */
public class PresenceInit4DelayProcessor implements Input {

	private final DelayElementBox delayElementBox;

	public PresenceInit4DelayProcessor(DelayElementBox delayElementBox) {
		super();
		this.delayElementBox = delayElementBox;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.presented() ? true : this.writeOffline(context);
	}

	private Boolean writeOffline(JIDContext context) {
		context.write(this.delayElementBox.pull(context.jid()), true);
		return true;
	}
}
