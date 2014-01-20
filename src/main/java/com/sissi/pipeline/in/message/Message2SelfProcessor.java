package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月12日
 */
public class Message2SelfProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		// Send to selfs it "to" is null
		return context.getJid().getUser().equals((protocol.getTo() != null ? super.build(protocol.getTo()) : context.getJid()).getUser()) ? this.writeAndReturn(context, protocol.setFrom(context.getJid())) : true;
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.getTo()), true).write(protocol);
		return false;
	}
}
