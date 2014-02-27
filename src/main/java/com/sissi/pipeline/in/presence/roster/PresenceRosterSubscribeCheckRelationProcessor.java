package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月26日
 */
public class PresenceRosterSubscribeCheckRelationProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.NONE.toString(), RosterSubscription.FROM.toString() };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		RelationRoster roster = super.ourRelation(super.build(protocol.getTo()), context.jid()).cast(RelationRoster.class);
		return roster.in(this.relations);
	}
}
