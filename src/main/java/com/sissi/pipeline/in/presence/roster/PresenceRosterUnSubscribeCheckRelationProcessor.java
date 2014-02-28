package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.roster.RelationRoster;

/**
 * @author kim 2014年1月26日
 */
public class PresenceRosterUnSubscribeCheckRelationProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.TO.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.jid(), super.build(protocol.getTo())).cast(RelationRoster.class).in(this.relations);
	}
}
