package com.sissi.pipeline.in.iq.si;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月13日
 */
public class SiProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(super.build(protocol.getParent().getTo()), protocol.getParent().setFrom(context.getJid().asStringWithBare()));
		return true;
	}
}
