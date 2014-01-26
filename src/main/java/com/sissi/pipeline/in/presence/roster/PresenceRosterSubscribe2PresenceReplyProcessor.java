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
public class PresenceRosterSubscribe2PresenceReplyProcessor extends ProxyProcessor {

	private final String[] relations = new String[] { RosterSubscription.TO.toString(), RosterSubscription.BOTH.toString() };

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.getJid(), super.build(protocol.getTo())).in(this.relations) ? this.writeAndReturn(context, protocol, super.build(protocol.getTo())) : true;
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol, JID to) {
		for (String resource : super.resources(to)) {
			super.broadcast(context.getJid(), new IQ().add(new Roster(new GroupItem(RelationRoster.class.cast(super.ourRelation(context.getJid(), to))))).setType(ProtocolType.SET));
			super.broadcast(context.getJid(), new Presence().setFrom(to.setResource(resource)).setType(PresenceType.SUBSCRIBED));
			super.broadcast(context.getJid(), new Presence().setFrom(to.setResource(resource)).setType(PresenceType.AVAILABLE));
		}
		return false;
	}
}
