package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.relation.roster.RosterRelation;
import com.sissi.ucenter.relation.roster.impl.PresenceRosterRelation;

/**
 * 订阅建立
 * 
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribeProcessor extends ProxyProcessor {

	/*
	 * 如果存在ASK则不再建立订阅
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		RosterRelation relation = super.ourRelation(context.jid(), super.build(protocol.getTo())).cast(RosterRelation.class);
		return relation.ask() ? true : this.establishAndReturn(context, protocol.cast(Presence.class), relation);
	}

	private boolean establishAndReturn(JIDContext context, Presence presence, RosterRelation relation) {
		super.establish(context.jid(), new PresenceRosterRelation(super.build(presence.getTo()), presence, relation));
		return true;
	}
}
