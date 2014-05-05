package com.sissi.pipeline.in.iq.roster.set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.relation.roster.RosterRelation;
import com.sissi.ucenter.relation.roster.impl.ItemRosterRelation;

/**
 * 订阅关系建立
 * 
 * @author kim 2013-10-31
 */
public class RosterSetProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		GroupItem item = protocol.cast(Roster.class).getFirstItem();
		super.establish(context.jid(), new ItemRosterRelation(super.ourRelation(context.jid(), super.build(item.getJid())).cast(RosterRelation.class), item));
		return true;
	}
}
