package com.sissi.pipeline.in.message;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.element.SubscriptionRequired;
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
		return this.isPass(context, super.build(protocol.getTo())) ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setTo(context.getJid()).setError(new ServerError().setType(Type.CANCEL).add(SubscriptionRequired.DETAIL)));
		return false;
	}

	private Boolean isPass(JIDContext context, JID slave) {
		return this.isFree || this.isSelf(context.getJid(), slave) || this.isSubscribed(context.getJid(), slave);
	}

	private Boolean isSelf(JID master, JID slave) {
		return master.getUser().equals(slave.getUser());
	}

	private Boolean isSubscribed(JID master, JID slave) {
		return this.hasRelation(super.ourRelation(master, slave));
	}

	private Boolean hasRelation(Relation relation) {
		return relation != null && (Roster.Subscription.BOTH.equals(relation.getSubscription()) || Roster.Subscription.TO.equals(relation.getSubscription()));
	}
}
