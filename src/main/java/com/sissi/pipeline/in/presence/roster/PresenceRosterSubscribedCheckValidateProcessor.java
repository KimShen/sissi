package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribedCheckValidateProcessor extends PresenceRosterCheckValidateProcessor {

	@Override
	protected JID getMaster(JIDContext context, Protocol protocol) {
		return super.build(protocol.getTo());
	}

	@Override
	protected JID getSlave(JIDContext context, Protocol protocol) {
		return context.getJid();
	}
}
