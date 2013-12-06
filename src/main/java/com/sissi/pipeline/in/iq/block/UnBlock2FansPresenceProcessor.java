package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.UnBlock;

/**
 * @author kim 2013年12月6日
 */
public class UnBlock2FansPresenceProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext freeContext = super.addressing.findOne(super.jidBuilder.build(UnBlock.class.cast(protocol).getItem().getJid()));
		super.presenceQueue.offer(freeContext.getJid(), context.getJid(), freeContext.getJid(), context.getPresence());
		return true;
	}

}
