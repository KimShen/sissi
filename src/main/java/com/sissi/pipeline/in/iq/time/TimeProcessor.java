package com.sissi.pipeline.in.iq.time;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年2月10日
 */
public class TimeProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.getParent().getTo()), true).write(protocol.getParent().setFrom(context.jid()));
		return false;
	}
}
