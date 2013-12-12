package com.sissi.pipeline.in.message;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.RelationContext.Relation;

/**
 * @author kim 2013-11-18
 */
public class MessageValidateProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.isSubscribed(context.getJid(), super.build(protocol.getTo()));
	}

	private boolean isSubscribed(JID master, JID slave) {
		return this.hasRelation(super.relationContext.ourRelation(master, slave));
	}

	private boolean hasRelation(Relation relation) {
		return relation != null && (Roster.Subscription.BOTH.equals(relation.getSubscription()) || Roster.Subscription.TO.equals(relation.getSubscription()));
	}
}
