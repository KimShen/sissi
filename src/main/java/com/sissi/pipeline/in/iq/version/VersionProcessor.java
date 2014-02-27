package com.sissi.pipeline.in.iq.version;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年2月10日
 */
public class VersionProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.parent().getTo()), true).write(protocol.parent().setFrom(context.jid()));
		return false;
	}
}
