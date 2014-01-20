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
abstract class Block2FansPresenceProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext target = super.find(super.build(Block.class.cast(protocol).getItem().getJid()));
		// deep copy
		JID from = super.build(context.getJid().asStringWithBare());
		for (String resource : super.resources(context.getJid())) {
			target.write(new Presence(from.setResource(resource), this.build(context).getClauses()));
		}
		return true;
	}

	abstract Status build(JIDContext context);
}