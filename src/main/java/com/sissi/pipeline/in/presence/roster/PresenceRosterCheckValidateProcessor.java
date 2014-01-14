package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2013-11-18
 */
abstract class PresenceRosterCheckValidateProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.isSubscribed(this.getMaster(context, protocol), this.getSlave(context, protocol));
	}

	private boolean isSubscribed(JID master, JID slave) {
		return this.canUpdate(super.ourRelation(master, slave));
	}

	private boolean canUpdate(Relation relation) {
		return relation != null && Roster.Subscription.NONE.equals(relation.getSubscription());
	}

	abstract protected JID getMaster(JIDContext context, Protocol protocol);

	abstract protected JID getSlave(JIDContext context, Protocol protocol);
}
