package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.roster.RelationRoster;
import com.sissi.ucenter.roster.impl.ItemWrapRelation;

/**
 * @author kim 2013-10-31
 */
public class RosterSetProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		GroupItem item = protocol.cast(Roster.class).getFirstItem();
		// convert jid to bare jid
		super.establish(context.jid(), new ItemWrapRelation(super.ourRelation(context.jid(), super.build(item.getJid())).cast(RelationRoster.class), item));
		return true;
	}
}
