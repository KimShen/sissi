package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013年12月12日
 */
abstract class Roster2SelfsItemProcessor extends ProxyProcessor {

	private final Group group;

	public Roster2SelfsItemProcessor(Group group) {
		super();
		this.group = group;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		GroupItem item = protocol.cast(Roster.class).getFirstItem();
		JID to = super.build(item.getJid());
		item.addOnEmpty(this.group).setAsk(this.ask()).setSubscription(this.subscription(context.jid(), to)).setJid(to.asStringWithBare());
		super.broadcast(context.jid(), protocol.parent());
		return this.next(item.getSubscription());
	}

	abstract protected boolean ask();

	abstract protected boolean next(String subscription);

	abstract protected RosterSubscription subscription(JID master, JID slave);
}
