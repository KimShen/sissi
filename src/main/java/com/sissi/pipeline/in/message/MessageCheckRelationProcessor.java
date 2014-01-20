package com.sissi.pipeline.in.message;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.SubscriptionRequired;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2013-11-18
 */
public class MessageCheckRelationProcessor extends ProxyProcessor {

	private final Boolean isFree;

	public MessageCheckRelationProcessor(Boolean isFree) {
		super();
		this.isFree = isFree;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.isPass(context, super.build(protocol.getTo())) ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(new ServerError().setType(ProtocolType.CANCEL).add(SubscriptionRequired.DETAIL)));
		return false;
	}

	private Boolean isPass(JIDContext context, JID slave) {
		// 1,Free 2,To Self 3,Has relation
		return this.isFree || context.getJid().getUser().equals(slave.getUser()) || this.hasRelation(super.ourRelation(context.getJid(), slave));
	}

	private Boolean hasRelation(Relation relation) {
		return relation != null && (RosterSubscription.BOTH.equals(relation.getSubscription()) || RosterSubscription.TO.equals(relation.getSubscription()));
	}
}
