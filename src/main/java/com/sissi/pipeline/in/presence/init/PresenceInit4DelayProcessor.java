package com.sissi.pipeline.in.presence.init;

import com.sissi.context.JIDContext;
import com.sissi.persistent.Persistent;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * 初始化离线XMPP节
 * 
 * @author kim 2014年1月21日
 */
public class PresenceInit4DelayProcessor implements Input {

	private final Persistent persistent;

	public PresenceInit4DelayProcessor(Persistent persistent) {
		super();
		this.persistent = persistent;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.writeDelay(context);
	}

	private boolean writeDelay(JIDContext context) {
		context.write(this.persistent.pull(context.jid()), true);
		return true;
	}
}
