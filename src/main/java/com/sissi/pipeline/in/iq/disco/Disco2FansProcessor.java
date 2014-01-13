package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月19日
 */
public class Disco2FansProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.addressing.findOne(super.build(protocol.getParent().getTo())).write(protocol.getParent().setFrom(context.getJid()));
		return true;
	}
}
