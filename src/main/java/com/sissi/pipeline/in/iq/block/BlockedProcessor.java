package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Block;
import com.sissi.protocol.iq.block.BlockListItem;
import com.sissi.ucenter.block.BlockContext;

/**
 * 加入黑名单
 * 
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
		for (BlockListItem item : protocol.cast(Block.class).getItem()) {
			this.blockContext.block(context.jid(), super.build(item.getJid()));
		}
		return true;
	}
}
