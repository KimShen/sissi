package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月21日
 */
public class PresenceInit4DelayProcessor implements Input {

	private final PersistentElementBox persistentElementBox;

	public PresenceInit4DelayProcessor(PersistentElementBox persistentElementBox) {
		super();
		this.persistentElementBox = persistentElementBox;
	}

	
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.presence() ? true : this.writeDelay(context);
	}

	private boolean writeDelay(JIDContext context) {
		context.write(this.persistentElementBox.pull(context.jid()), true, true);
		return true;
	}
}
