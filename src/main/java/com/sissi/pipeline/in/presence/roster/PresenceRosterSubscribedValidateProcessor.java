package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribedValidateProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.isSubscribed(super.build(protocol.getTo()), context.getJid());
	}

	private boolean isSubscribed(JID master, JID slave) {
		return this.hasRelation(super.relationContext.ourRelation(master, slave));
	}

	private boolean hasRelation(Relation relation) {
		return relation != null && Roster.Subscription.NONE.equals(relation.getSubscription());
	}
}
