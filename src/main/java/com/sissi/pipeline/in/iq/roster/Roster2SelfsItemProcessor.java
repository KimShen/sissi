package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013年12月12日
 */
abstract class Roster2SelfsItemProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		GroupItem item = Roster.class.cast(protocol).getFirstItem();
		JID to = super.build(item.getJid());
		item.setAsk(this.isAsk()).setSubscription(this.subscription(context.jid(), to)).setJid(to.asStringWithBare());
		super.broadcast(context.jid(), protocol.getParent());
		return this.isNext(item.getSubscription());
	}

	abstract protected boolean isAsk();

	abstract protected boolean isNext(String subscription);

	abstract protected RosterSubscription subscription(JID master, JID slave);
}
