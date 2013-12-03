package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribeRelationProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return !this.isSubscribed(context, protocol);
	}

	private boolean isSubscribed(JIDContext context, Protocol protocol) {
		return this.hasRelation(super.relationContext.ourRelation(context.getJid(), super.jidBuilder.build(protocol.getTo())));
	}

	private boolean hasRelation(Relation relation) {
		return relation != null && (Roster.Subscription.BOTH.equals(relation.getSubscription()) || Roster.Subscription.TO.equals(relation.getSubscription()));
	}
}
