package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.UnBlock;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.ucenter.BanContext;

/**
 * @author kim 2013年12月6日
 */
public class UnBlockProcessor extends UtilProcessor {

	private final BanContext banContext;

	public UnBlockProcessor(BanContext banContext) {
		super();
		this.banContext = banContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		UnBlock ub = UnBlock.class.cast(protocol);
		return ub.isUnBlockAll() ? writeAndReturn(context) : writeAndReturn(context, ub.getItem());
	}

	private Boolean writeAndReturn(JIDContext context, Item item) {
		this.banContext.free(context.getJid(), super.build(item.getJid()));
		return true;
	}

	private Boolean writeAndReturn(JIDContext context) {
		this.banContext.free(context.getJid());
		return true;
	}
}
