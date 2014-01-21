package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.offline.DelayElementBox;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月21日
 */
public class PresenceInit4OfflineProcessor implements Input {

	private final DelayElementBox delayElementBox;

	public PresenceInit4OfflineProcessor(DelayElementBox delayElementBox) {
		super();
		this.delayElementBox = delayElementBox;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isPresence() ? true : init4Offline(context);
	}

	private Boolean init4Offline(JIDContext context) {
		context.write(this.delayElementBox.pull(context.getJid()));
		return true;
	}
}
