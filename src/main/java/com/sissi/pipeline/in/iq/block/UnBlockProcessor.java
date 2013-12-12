package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.UnBlock;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.ucenter.BlockContext;

/**
 * @author kim 2013年12月6日
 */
public class UnBlockProcessor extends ProxyProcessor {

	private final BlockContext blockContext;

	public UnBlockProcessor(BlockContext blockContext) {
		super();
		this.blockContext = blockContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		UnBlock ub = UnBlock.class.cast(protocol);
		return ub.isUnBlockAll() ? writeAndReturn(context) : writeAndReturn(context, ub.getItem());
	}

	private Boolean writeAndReturn(JIDContext context, Item item) {
		this.blockContext.unblock(context.getJid(), super.build(item.getJid()));
		return true;
	}

	private Boolean writeAndReturn(JIDContext context) {
		this.blockContext.unblock(context.getJid());
		return true;
	}
}
