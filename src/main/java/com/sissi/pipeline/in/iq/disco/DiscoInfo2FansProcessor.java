package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月19日
 */
public class DiscoInfo2FansProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(super.build(protocol.getTo()), protocol.setFrom(context.getJid().asStringWithBare()).getParent());
		return true;
	}
}
