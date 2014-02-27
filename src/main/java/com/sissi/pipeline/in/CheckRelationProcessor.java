package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月26日
 */
abstract public class CheckRelationProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.TO.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.ourRelation(context, protocol) ? true : this.writeAndReturn(context, protocol);
	}

	protected boolean ourRelation(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(RelationRoster.class).in(this.relations);
	}

	abstract protected boolean writeAndReturn(JIDContext context, Protocol protocol);
}
