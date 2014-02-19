package com.sissi.pipeline.in.presence.roster;

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
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月24日
 */
public class PresenceRosterSubscribe2ReplySelfsProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.TO.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return RelationRoster.class.cast(super.ourRelation(context.jid(), super.build(protocol.getTo()))).in(this.relations) ? this.writeAndReturn(context, super.build(protocol.getTo()), Presence.class.cast(protocol)) : true;
	}

	private Boolean writeAndReturn(JIDContext context, JID to, Presence presence) {
		this.writeRoster(context, to).writeSubscribed(context, to, presence.clear()).writeAvailable(context, to, presence.clear());
		return false;
	}

	private PresenceRosterSubscribe2ReplySelfsProcessor writeRoster(JIDContext context, JID to) {
		super.broadcast(context.jid(), new IQ().add(new Roster(new GroupItem(RelationRoster.class.cast(super.ourRelation(context.jid(), to))))).setType(ProtocolType.SET));
		return this;
	}

	private PresenceRosterSubscribe2ReplySelfsProcessor writeSubscribed(JIDContext context, JID to, Presence presence) {
		super.broadcast(context.jid(), presence.setType(PresenceType.SUBSCRIBED).setFrom(to.asStringWithBare()));
		return this;
	}

	private PresenceRosterSubscribe2ReplySelfsProcessor writeAvailable(JIDContext context, JID to, Presence presence) {
		presence.setType(PresenceType.AVAILABLE);
		for (JID resource : super.resources(to)) {
			super.broadcast(context.jid(), presence.setFrom(resource).clauses(super.findOne(resource, true).status().clauses()));
		}
		return this;
	}
}
