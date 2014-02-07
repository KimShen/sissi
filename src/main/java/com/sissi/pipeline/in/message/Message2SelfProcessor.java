package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月12日
 */
public class Message2SelfProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() || context.jid().user(protocol.getTo()) ? this.writeAndReturn(context, protocol.setFrom(context.jid())) : true;
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.getTo()), true).write(protocol);
		return false;
	}
}
