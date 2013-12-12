package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.RelationContext.Relation;
import com.sissi.ucenter.RelationContext.RelationRoster;

/**
 * @author kim 2013-10-31
 */
public class RosterGetProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(IQ.class.cast(protocol.getParent().reply().setTo(context.getJid().getBare()).setType(Type.RESULT)).add(this.prepare(context, Roster.class.cast(protocol).clear())));
		return false;
	}

	private Roster prepare(JIDContext context, Roster roster) {
		for (Relation each : super.myRelations(context.getJid())) {
			RelationRoster relation = RelationRoster.class.cast(each);
			roster.add(new Item().setName(relation.getName()).setGroup(new Group(relation.asGroup())).setSubscription(relation.getSubscription()).setJid(each.getJID()));
		}
		return roster;
	}
}
