package com.sissi.pipeline.in.presence.probe;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月27日
 */
public class PresenceProbeRelationProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.getJid(), super.build(protocol.getTo())).in(RosterSubscription.FROM.toString(), RosterSubscription.BOTH.toString()) ? true : this.writeAndReturn(context, Presence.class.cast(protocol));
	}

	private Boolean writeAndReturn(JIDContext context, Presence presence) {
		context.write(presence.clear().reply().setType(PresenceType.UNSUBSCRIBED.toString()));
		return false;
	}
}
