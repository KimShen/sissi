package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * 用户-用户转发(Using resource)
 * 
 * @author kim 2013年12月19日
 */
public class ForwardProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.parent().getTo()), true).write(protocol.parent().setFrom(context.jid()));
		return true;
	}
}
