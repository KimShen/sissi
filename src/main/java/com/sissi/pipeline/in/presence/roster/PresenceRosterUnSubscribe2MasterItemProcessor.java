package com.sissi.pipeline.in.presence.roster;

import java.util.UUID;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribe2MasterItemProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.getJid(), this.prepare(context.getJid(), super.build(protocol.getTo()), protocol));
		return true;
	}

	private Protocol prepare(JID master, JID slave, Protocol protocol) {
		Relation relation = super.ourRelation(master, slave);
		return new IQ().add(new Roster().add(new GroupItem(slave.asStringWithBare(), relation.getName(), Roster.Subscription.NONE.toString(), RelationRoster.class.cast(relation).asGroup()))).setTo(master).setId(UUID.randomUUID().toString()).setType(Type.SET);
	}
}
