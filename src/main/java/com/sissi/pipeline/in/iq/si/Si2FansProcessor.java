package com.sissi.pipeline.in.iq.si;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月13日
 */
public class Si2FansProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext target = super.addressing.findOne(super.build(protocol.getParent().getTo()));
		target.write(protocol.getParent().setFrom(context.getJid().getBare()).setTo(target.getJid().getBare()));
		return true;
	}
}
