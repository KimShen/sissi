package com.sissi.pipeline.in.presence.probe;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月27日
 */
public class PresenceProbeRelationProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return RelationRoster.class.cast(super.ourRelation(context.jid(), super.build(protocol.getTo()))).in(RosterSubscription.TO, RosterSubscription.BOTH) ? true : this.writeAndReturn(context, Presence.class.cast(protocol));
	}

	private Boolean writeAndReturn(JIDContext context, Presence presence) {
		context.write(presence.clear().reply().setType(PresenceType.UNSUBSCRIBED.toString()));
		return false;
	}
}
