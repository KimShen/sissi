package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.UnBlock;

/**
 * @author kim 2013年12月6日
 */
public class UnBlock2FansPresenceProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext contacter = super.findOne(super.build(UnBlock.class.cast(protocol).getItem().getJid()));
		super.offer(contacter.getJid(), context.getJid(), contacter.getJid(), context.getStatus());
		return true;
	}

}
