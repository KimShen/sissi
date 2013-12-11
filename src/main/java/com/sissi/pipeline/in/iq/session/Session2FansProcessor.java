package com.sissi.pipeline.in.iq.session;

import java.util.UUID;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.X;

/**
 * @author kim 2013-10-29
 */
public class Session2FansProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.protocolQueue.offer(context.getJid().getBare(), new Presence().setX(new X(UUID.randomUUID().toString())).setFrom(context.getJid().asStringWithBare()));
		return true;
	}
}
