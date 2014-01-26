package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2014年1月26日
 */
public class PresenceRosterUnSubscribeCheckRelationProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.FROM.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.getJid(), super.build(protocol.getTo())).in(this.relations);
	}
}
