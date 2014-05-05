package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * 订阅更新
 * 
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribedProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.update(super.build(protocol.getTo()), context.jid(), RosterSubscription.TO.toString());
		return true;
	}
}
