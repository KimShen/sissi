package com.sissi.pipeline.in.presence.roster;

import java.util.UUID;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * 自动批准.</p>1,名册推送 2,Presence type = subscribed 3,To所有资源对当前JID上线
 * 
 * @author kim 2014年1月24日
 */
public class PresenceRosterSubscribe2ReplySelfsProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.TO.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		return super.ourRelation(context.jid(), to).cast(RosterRelation.class).in(this.relations) ? this.writeAndReturn(context, to, protocol.cast(Presence.class)) : true;
	}

	private boolean writeAndReturn(JIDContext context, JID to, Presence presence) {
		this.writeRoster(context, to).writeSubscribed(context, to, presence).writeAvailable(context, to, presence);
		return false;
	}

	private PresenceRosterSubscribe2ReplySelfsProcessor writeRoster(JIDContext context, JID to) {
		super.broadcast(context.jid(), new IQ().setId(UUID.randomUUID().toString()).add(new Roster(new GroupItem(super.ourRelation(context.jid(), to).cast(RosterRelation.class)))).setType(ProtocolType.SET));
		return this;
	}

	private PresenceRosterSubscribe2ReplySelfsProcessor writeSubscribed(JIDContext context, JID to, Presence presence) {
		super.broadcast(context.jid(), presence.type(PresenceType.SUBSCRIBED).setFrom(to.asStringWithBare()));
		return this;
	}

	private PresenceRosterSubscribe2ReplySelfsProcessor writeAvailable(JIDContext context, JID to, Presence presence) {
		for (JID resource : super.resources(to)) {
			super.broadcast(context.jid(), presence.type(PresenceType.AVAILABLE).setFrom(resource).clauses(super.findOne(resource, true).status().clauses()));
		}
		return this;
	}
}
