package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class Message2FanProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.addressing.findOne(super.build(protocol.getTo())).write(protocol.setFrom(context.getJid()));
		return true;
	}
}
