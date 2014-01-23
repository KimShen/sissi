package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterCheckSubscribeRelationProcessor extends PresenceRosterCheckRelationProcessor {

	protected Boolean shouldActivate() {
		return false;
	}

	@Override
	protected JID getMaster(JID context, JID to) {
		return context;
	}

	@Override
	protected JID getSlave(JID context, JID to) {
		return to;
	}
}
