package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * Presence type = unsubscribed关系校验(TO,BOTH)
 * 
 * @author kim 2014年1月26日
 */
public class PresenceRosterUnSubscribedCheckRelationProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.TO.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		RosterRelation roster = super.ourRelation(super.build(protocol.getTo()), context.jid()).cast(RosterRelation.class);
		return roster.in(this.relations) || roster.ask();
	}
}
