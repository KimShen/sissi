package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.Relation.RelationRoster;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribe2SourceItemProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.protocolQueue.offer(context.getJid(), this.prepare(context.getJid(), super.build(protocol.getTo()), protocol));
		return true;
	}

	private Protocol prepare(JID master, JID slave, Protocol protocol) {
		Relation relation = super.relationContext.ourRelation(master, slave);
		return new IQ(Type.SET).setTo(master).add(new Roster().add(new Item(slave.asStringWithBare(), relation.getName(), Roster.Subscription.NONE.toString(), RelationRoster.class.cast(relation).asGroup())));
	}
}
