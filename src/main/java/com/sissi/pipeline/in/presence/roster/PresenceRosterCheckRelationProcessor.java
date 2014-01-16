package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2013-11-18
 */
abstract class PresenceRosterCheckRelationProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.canUpdate(super.ourRelation(this.getMaster(context, protocol), this.getSlave(context, protocol)));
	}

	private boolean canUpdate(Relation relation) {
		return relation != null && RosterSubscription.NONE.equals(relation.getSubscription());
	}

	abstract protected JID getMaster(JIDContext context, Protocol protocol);

	abstract protected JID getSlave(JIDContext context, Protocol protocol);
}
