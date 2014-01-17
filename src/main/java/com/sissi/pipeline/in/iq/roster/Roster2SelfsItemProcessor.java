package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月12日
 */
abstract class Roster2SelfsItemProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		String subscription = this.getSubscription(context.getJid(), super.build(roster.getFirstItem().getJid()));
		roster.getFirstItem().setSubscription(this.getSubscription(context.getJid(), super.build(roster.getFirstItem().getJid())));
		super.broadcast(context.getJid(), protocol.getParent());
		return this.getNextWhenThisSubscription(subscription);
	}

	abstract protected Boolean getNextWhenThisSubscription(String subscription);

	abstract protected String getSubscription(JID master, JID slave);
}
