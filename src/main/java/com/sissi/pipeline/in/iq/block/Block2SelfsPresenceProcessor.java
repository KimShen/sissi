package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Block;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013年12月12日
 */
abstract class Block2SelfsPresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.cast(Block.class).getItem().getJid());
		Presence presence = new Presence();
		for (JID resource : super.resources(to)) {
			super.broadcast(context.jid(), presence.setFrom(resource).clauses(this.build(super.findOne(to, true, true)).clauses()));
		}
		return true;
	}

	abstract protected Status build(JIDContext contacter);
}