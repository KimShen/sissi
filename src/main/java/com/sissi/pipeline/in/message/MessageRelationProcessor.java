package com.sissi.pipeline.in.message;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2013-11-18
 */
public class MessageRelationProcessor extends ProxyProcessor {

	private final Boolean isFree;

	public MessageRelationProcessor(Boolean isFree) {
		super();
		this.isFree = isFree;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.isSubscribed(context.getJid(), super.build(protocol.getTo())) || this.isFree;
	}

	private boolean isSubscribed(JID master, JID slave) {
		return this.hasRelation(super.ourRelation(master, slave));
	}

	private boolean hasRelation(Relation relation) {
		return relation != null && (Roster.Subscription.BOTH.equals(relation.getSubscription()) || Roster.Subscription.TO.equals(relation.getSubscription()));
	}
}
