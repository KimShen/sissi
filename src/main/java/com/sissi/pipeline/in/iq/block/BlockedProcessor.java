package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Blocked;
import com.sissi.ucenter.user.BlockContext;

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
	public boolean input(JIDContext context, Protocol protocol) {
		this.blockContext.block(context.jid(), super.build(protocol.cast(Blocked.class).getItem().getJid()));
		return true;
	}
}
