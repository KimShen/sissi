package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月24日
 */
public class PresenceRosterSubscribe2ReplyFansProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.FROM.toString() };

	private final Input proxy;

	public PresenceRosterSubscribe2ReplyFansProcessor(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		return super.ourRelation(context.jid(), to).cast(RelationRoster.class).in(this.relations) ? this.writeAndReturn(context, to, Presence.class.cast(protocol)) : true;
	}

	private boolean writeAndReturn(JIDContext context, JID to, Presence presence) {
		this.proxy.input(super.findOne(to), presence.setType(PresenceType.SUBSCRIBED).setTo(context.jid().asStringWithBare()));
		return false;
	}
}
