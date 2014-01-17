package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JIDContext;
import com.sissi.offline.DelayElementBox;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月17日
 */
public class Session4OfflineProcessor implements Input {

	private final DelayElementBox delayElementBox;

	public Session4OfflineProcessor(DelayElementBox delayElementBox) {
		super();
		this.delayElementBox = delayElementBox;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.delayElementBox.pull(context.getJid()));
		return true;
	}
}
