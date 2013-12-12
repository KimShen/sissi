package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.Relation.RelationRoster;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribed2SourceItemProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID source = super.build(protocol.getTo());
		super.protocolQueue.offer(source, this.prepare(context, source));
		return true;
	}

	private IQ prepare(JIDContext context, JID jid) {
		RelationRoster relation = RelationRoster.class.cast(super.relationContext.ourRelation(jid, context.getJid()));
		return new IQ(Type.SET).setTo(jid).add(new Roster(new Item(context.getJid().asStringWithBare(), relation.getName(), Roster.Subscription.TO.toString(), relation.asGroup())));
	}
}
