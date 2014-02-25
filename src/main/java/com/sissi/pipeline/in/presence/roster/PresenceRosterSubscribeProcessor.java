package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.RelationRoster;
import com.sissi.ucenter.relation.PresenceRosterWrapRelation;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribeProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		RelationRoster relation = super.ourRelation(context.jid(), super.build(protocol.getTo())).cast(RelationRoster.class);
		return relation.isAsk() ? true : this.establishAndReturn(context, protocol.cast(Presence.class), relation);
	}

	private boolean establishAndReturn(JIDContext context, Presence presence, RelationRoster relation) {
		super.establish(context.jid(), new PresenceRosterWrapRelation(super.build(presence.getTo()), presence, relation));
		return true;
	}
}
