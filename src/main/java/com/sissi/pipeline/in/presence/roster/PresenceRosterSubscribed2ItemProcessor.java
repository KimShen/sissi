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
import com.sissi.ucenter.roster.RelationRoster;

/**
 * @author kim 2013-11-18
 */
public class PresenceRosterSubscribed2ItemProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID to = super.build(protocol.getTo());
		super.broadcast(to, new IQ().setId(UUID.randomUUID().toString()).add(new Roster(new GroupItem(super.ourRelation(to, context.jid()).cast(RelationRoster.class)))).setType(ProtocolType.SET));
		return true;
	}
}
