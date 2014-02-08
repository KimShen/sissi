package com.sissi.pipeline.in.message;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.SubscriptionRequired;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-18
 */
public class MessageCheckRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(SubscriptionRequired.DETAIL);

	private final Boolean isFree;

	public MessageCheckRelationProcessor(Boolean isFree) {
		super();
		this.isFree = isFree;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.isPass(context, super.build(protocol.getTo())) ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(this.error));
		return false;
	}

	private Boolean isPass(JIDContext context, JID slave) {
		// 1,Free 2,To Self 3,Has relation
		return this.isFree || context.jid().user(slave) || RelationRoster.class.cast(super.ourRelation(context.jid(), slave)).in(RosterSubscription.BOTH, RosterSubscription.TO);
	}
}
