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
 * Roster名册推送
 * 
 * @author kim 2013年12月12日
 */
abstract public class RosterItemProcessor extends ProxyProcessor {

	private final Group group;

	/**
	 * @param group 默认Group
	 */
	public RosterItemProcessor(Group group) {
		super();
		this.group = group;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		GroupItem item = protocol.cast(Roster.class).first();
		JID to = super.build(item.getJid());
		item.addOnEmpty(this.group).setAsk(this.ask()).setSubscription(this.subscription(context.jid(), to)).setJid(to.asStringWithBare());
		// Roster Set/Remove From必须=null
		super.broadcast(context.jid(), protocol.parent().setFrom((String) null));
		return true;
	}

	/**
	 * 实现类是否附加ASK
	 * 
	 * @return
	 */
	abstract protected boolean ask();

	/**
	 * 实现类附加的订阅关系
	 * 
	 * @param master
	 * @param slave
	 * @return
	 */
	abstract protected RosterSubscription subscription(JID master, JID slave);
}
