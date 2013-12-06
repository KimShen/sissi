package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013年12月5日
 */
public class Blocked2SelfsItemProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.protocolQueue.offer(context.getJid(), protocol.getParent().setTo(context.getJid().getBare()).setType(Type.SET));
		return true;
	}
}
