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
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribed2SourceItemProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID source = super.build(protocol.getTo());
		super.protocolQueue.offer(source, this.prepare(source, context.getJid(), protocol));
		return true;
	}

	private Protocol prepare(JID master, JID slave, Protocol protocol) {
		Relation relation = super.relationContext.ourRelation(master, slave);
		return new IQ(Type.SET).setTo(master).add(new Roster().add(new Item(slave.asStringWithBare(), relation.getName(), Roster.Subscription.NONE.toString(), RelationRoster.class.cast(relation).asGroup())));
	}
}
