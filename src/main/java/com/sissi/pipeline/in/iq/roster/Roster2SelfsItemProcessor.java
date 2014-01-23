package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月12日
 */
abstract class Roster2SelfsItemProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		GroupItem item = Roster.class.cast(protocol).getFirstItem();
		JID to = super.build(item.getJid());
		// synchronize subscription from db
		// convert jid to bare jid (if necessary)
		item.setAsk(this.isAsk()).setSubscription(this.subscription(context.getJid(), to)).setJid(to.asStringWithBare());
		super.broadcast(context.getJid(), protocol.getParent());
		return this.isNext(item.getSubscription());
	}

	abstract protected String subscription(JID master, JID slave);

	abstract protected Boolean isAsk();
	
	abstract protected Boolean isNext(String subscription);
}
