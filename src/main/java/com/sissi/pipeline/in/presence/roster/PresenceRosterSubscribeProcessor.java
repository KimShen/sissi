package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationRoster;
import com.sissi.ucenter.relation.PresenceWrapRelation;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribeProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		RelationRoster relation = RelationRoster.class.cast(super.ourRelation(context.getJid(), super.build(protocol.getTo())));
		return relation.isAsk() ? true : this.establishAndReturn(context, Presence.class.cast(protocol), relation);
	}

	private Boolean establishAndReturn(JIDContext context, Presence presence, Relation relation) {
		super.establish(context.getJid(), new PresenceWrapRelation(super.jidBuilder, presence, relation));
		return true;
	}
}
