package com.sissi.pipeline.in.iq.block;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.BlockListItem;
import com.sissi.protocol.iq.block.UnBlock;
import com.sissi.ucenter.block.BlockContext;

/**
 * 黑名单移除
 * 
 * @author kim 2013年12月6日
 */
public class UnBlockProcessor extends ProxyProcessor {

	private final BlockContext blockContext;

	public UnBlockProcessor(BlockContext blockContext) {
		super();
		this.blockContext = blockContext;
	}

	/*
	 * Unblock不含Item则表示全部移除
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		UnBlock ub = protocol.cast(UnBlock.class);
		return ub.unBlockAll() ? this.unblockAndReturn(context) : this.unblockAndReturn(context, ub.getItem());
	}

	private boolean unblockAndReturn(JIDContext context, List<BlockListItem> item) {
		for (BlockListItem each : item) {
			this.blockContext.unblock(context.jid(), super.build(each.getJid()));
		}
		return true;
	}

	private boolean unblockAndReturn(JIDContext context) {
		this.blockContext.unblock(context.jid());
		return true;
	}
}
