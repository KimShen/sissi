package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013年12月12日
 */
abstract class Block2SelfsItemProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.getJid(), protocol.getParent().setTo(context.getJid().getBare()).setType(Type.SET));
		return true;
	}
}
