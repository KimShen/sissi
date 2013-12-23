package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.RelationContext.Relation;
import com.sissi.ucenter.RelationContext.RelationRoster;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribed2MasterItemProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID master = super.build(protocol.getTo());
		super.broadcast(master, this.prepare(context, master));
		return true;
	}

	private Protocol prepare(JIDContext context, JID jid) {
		Relation relation = super.ourRelation(jid, context.getJid());
		return new IQ().add(new Roster(new GroupItem(context.getJid().asStringWithBare(), relation.getName(), Roster.Subscription.TO.toString(), RelationRoster.class.cast(relation).asGroup()))).setTo(jid.getBare()).setType(Type.SET);
	}
}
