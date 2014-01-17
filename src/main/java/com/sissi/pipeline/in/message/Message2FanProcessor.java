package com.sissi.pipeline.in.message;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class Message2FanProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		super.addressing.findOne(to, to.getResource() != null).write(protocol.setFrom(context.getJid()));
		return true;
	}
}
