package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-17
 */
public class PresenceRosterUnsubscribedProcessor extends UtilProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.relationContext.remove(super.jidBuilder.build(protocol.getTo()), context.getJid());
		return true;
	}
}
