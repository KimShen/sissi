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
public class Blocked2SelfsPresenceProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JIDContext contacter = super.addressing.findOne(super.jidBuilder.build(Blocked.class.cast(protocol).getItem().getJid()));
		super.presenceQueue.offer(context.getJid(), contacter.getJid(), context.getJid(), new Presence().setType(Type.UNAVAILABLE));
		return true;
	}
}
