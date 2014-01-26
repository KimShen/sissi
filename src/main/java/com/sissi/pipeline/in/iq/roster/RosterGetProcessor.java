package com.sissi.pipeline.in.iq.roster;

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
 * @author kim 2013-10-31
 */
public class RosterGetProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(IQ.class.cast(protocol.getParent().clear().reply().setType(ProtocolType.RESULT)).add(this.prepare(context, Roster.class.cast(protocol))));
		return false;
	}

	private Roster prepare(JIDContext context, Roster roster) {
		for (Relation each : super.myRelations(context.getJid())) {
			roster.add(new GroupItem(RelationRoster.class.cast(each)));
		}
		return roster;
	}
}
