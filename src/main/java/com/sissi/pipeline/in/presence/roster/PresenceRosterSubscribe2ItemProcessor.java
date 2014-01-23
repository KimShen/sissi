package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月23日
 */
public class PresenceRosterSubscribe2ItemProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Relation relation = super.ourRelation(context.getJid(), super.build(protocol.getTo()));
		super.broadcast(context.getJid(), new IQ().add(new Roster(new GroupItem(relation.getJID(), relation.getName(), true, relation.getSubscription(), RelationRoster.class.cast(relation).asGroups()))).setType(ProtocolType.SET));
		return true;
	}
}
