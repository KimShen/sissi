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
		JID to = super.build(protocol.getTo());
		return this.canUpdate(super.ourRelation(this.getMaster(context.getJid(), to), this.getSlave(context.getJid(), to)));
	}

	private boolean canUpdate(Relation relation) {
		return (!this.shouldActivate() || relation.isActivate()) && (RosterSubscription.NONE.equals(relation.getSubscription()) || RosterSubscription.FROM.equals(relation.getSubscription()));
	}

	abstract protected Boolean shouldActivate();
	
	abstract protected JID getMaster(JID current, JID to);

	abstract protected JID getSlave(JID current, JID to);
}
