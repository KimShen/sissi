package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年2月25日
 */
public class PresenceInit4MockProcessor extends ProxyProcessor {

	private final PersistentElementBox persistentElementBox;

	public PresenceInit4MockProcessor(PersistentElementBox persistentElementBox) {
		super();
		this.persistentElementBox = persistentElementBox;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(this.persistentElementBox.pull(context.jid()), false, true);
		return false;
	}

}
