package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationRoster;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribedAndBroadcastProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		JID jid = super.jidBuilder.build(protocol.getTo());
		super.protocolQueue.offer(jid.getBare(), this.generateResponse(context, jid.getBare()));
		return true;
	}

	private IQ generateResponse(JIDContext context, JID jid) {
		Relation relation = super.relationContext.ourRelation(jid.getBare(), context.getJid().getBare());
		return ((IQ) new IQ(Type.SET).setTo(jid.getBare())).add(new Roster(new Item(context.getJid().asStringWithBare(), relation.getName(), Roster.Subscription.TO.toString(), RelationRoster.class.cast(relation).getGp())));
	}
}
