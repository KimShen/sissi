package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * 同JID不同资源间转发
 * 
 * @author kim 2014年1月12日
 */
public class Message2SelfProcessor extends ProxyProcessor {

	/*
	 * 不含To或To.bare与当前JID.bare相同
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() || context.jid().like(protocol.getTo()) ? this.writeAndReturn(context, protocol.setFrom(context.jid())) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.getTo()), true).write(protocol);
		return false;
	}
}
