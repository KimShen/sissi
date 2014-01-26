package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.RelationRoster;
import com.sissi.ucenter.relation.ItemWrapRelation;

/**
 * @author kim 2013-10-31
 */
public class RosterSetProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		GroupItem item = Roster.class.cast(protocol).getFirstItem();
		RelationRoster relation = RelationRoster.class.cast(super.ourRelation(context.getJid(), super.build(item.getJid())));
		// convert jid to bare jid
		super.establish(context.getJid(), new ItemWrapRelation(relation, item));
		return true;
	}
}
