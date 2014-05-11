package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Block;
import com.sissi.protocol.iq.block.BlockListItem;
import com.sissi.protocol.presence.Presence;

/**
 * 广播Presence(From: Block.item.jid所有资源, To:当前JID所有资源)
 * 
 * @author kim 2013年12月12日
 */
abstract class ToSelfsPresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		for (BlockListItem item : protocol.cast(Block.class).getItem()) {
			JID to = super.build(item.getJid());
			for (JID resource : super.resources(to)) {
				// super.findOne(to, true, true) -> Block.item.jid指定资源的JIDContext
				super.broadcast(context.jid(), presence.setFrom(resource).clauses(this.build(super.findOne(to, true, true)).clauses()));
			}
		}
		return true;
	}

	abstract protected Status build(JIDContext contacter);
}