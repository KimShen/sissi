package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * 用户-用户转发(Without resource)
 * 
 * @author kim 2013年12月19日
 */
public class ForwardsProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.find(super.build(protocol.parent().getTo())).write(protocol.parent().setFrom(context.jid()));
		return true;
	}
}
