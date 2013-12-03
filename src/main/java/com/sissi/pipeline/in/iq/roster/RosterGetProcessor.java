package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationRoster;

/**
 * @author kim 2013-10-31
 */
public class RosterGetProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.preparpreResponse(protocol).add(this.prepareRelation(context, (Roster) Roster.class.cast(protocol).clear())));
		return false;
	}

	private Roster prepareRelation(JIDContext context, Roster roster) {
		for (Relation each : super.relationContext.myRelations(context.getJid())) {
			RelationRoster relation = RelationRoster.class.cast(each);
			roster.add((Item)new Item().setName(relation.getName()).setGroup(new Group(relation.getGroupText())).setSubscription(relation.getSubscription()).setJid(each.getJID()));
		}
		return roster;
	}

	private IQ preparpreResponse(Protocol protocol) {
		return (IQ) protocol.getParent().reply().clear().setType(Type.RESULT);
	}
}
