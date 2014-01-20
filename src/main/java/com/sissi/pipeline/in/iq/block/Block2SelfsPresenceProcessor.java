package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Block;

/**
 * @author kim 2013年12月12日
 */
abstract class Block2SelfsPresenceProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext contacter = super.findOne(super.build(Block.class.cast(protocol).getItem().getJid()));
		super.broadcast(context.getJid(), contacter.getJid(), this.build(contacter));
		return true;
	}

	abstract protected Status build(JIDContext contacter);
}