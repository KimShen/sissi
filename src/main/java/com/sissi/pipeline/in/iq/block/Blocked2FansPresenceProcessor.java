package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.block.Blocked;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013年12月6日
 */
public class Blocked2FansPresenceProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext freeContext = super.addressing.findOne(super.jidBuilder.build(Blocked.class.cast(protocol).getItem().getJid()));
		super.presenceQueue.offer(freeContext.getJid(), context.getJid(), freeContext.getJid(), new Presence().setType(Type.UNAVAILABLE));
		return true;
	}
}
