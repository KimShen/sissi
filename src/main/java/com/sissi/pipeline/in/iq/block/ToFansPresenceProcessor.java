package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Block;
import com.sissi.protocol.presence.Presence;

/**
 * 广播Presence(From: 当前JID所有资源, To: Block.item.jid所有资源)
 * 
 * @author kim 2013年12月12日
 */
abstract class ToFansPresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		JID to = super.build(protocol.cast(Block.class).getItem().getJid());
		// From: 当前JID所有资源, To: Block.item.jid
		for (JID resource : super.resources(context.jid())) {
			super.broadcast(to, presence.setFrom(resource).clauses(this.build(context).clauses()));
		}
		return true;
	}

	abstract protected Status build(JIDContext context);
}