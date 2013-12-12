package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Blocked;
import com.sissi.ucenter.BlockContext;

/**
 * @author kim 2013年12月5日
 */
public class BlockedProcessor extends ProxyProcessor {

	private final BlockContext blockContext;

	public BlockedProcessor(BlockContext blockContext) {
		super();
		this.blockContext = blockContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.blockContext.block(context.getJid(), super.build(Blocked.class.cast(protocol).getItem().getJid()));
		return true;
	}
}
