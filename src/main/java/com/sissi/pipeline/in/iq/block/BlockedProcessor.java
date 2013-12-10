package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Blocked;
import com.sissi.ucenter.BanContext;

/**
 * @author kim 2013年12月5日
 */
public class BlockedProcessor extends UtilProcessor {

	private final BanContext banContext;

	public BlockedProcessor(BanContext banContext) {
		super();
		this.banContext = banContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.banContext.ban(context.getJid(), super.build(Blocked.class.cast(protocol).getItem().getJid()));
		return true;
	}
}
