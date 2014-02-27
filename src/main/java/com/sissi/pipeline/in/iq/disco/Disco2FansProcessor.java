package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月19日
 */
public class Disco2FansProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.parent().getTo()), true).write(protocol.parent().setFrom(context.jid()));
		return true;
	}
}
