package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月26日
 */
public class PresenceRosterUnSubscribedCheckRelationProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.FROM.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		RelationRoster roster = RelationRoster.class.cast(super.ourRelation(super.build(protocol.getTo()), context.getJid()));
		return roster.in(this.relations) || roster.isAsk();
	}
}
