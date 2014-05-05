package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.persistent.Persistent;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * 持久化
 * 
 * @author kim 2014年3月3日
 */
public class MessagePersistentProcessor extends ProxyProcessor {

	private final Persistent persistent;

	public MessagePersistentProcessor(Persistent persistent) {
		super();
		this.persistent = persistent;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.persistent.push(protocol.parent().setFrom(context.jid()));
		return true;
	}
}
