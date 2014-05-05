package com.sissi.pipeline.in.presence.roster;

import java.util.UUID;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * Presence type = subscribe时向To广播名册推送
 * 
 * @author kim 2014年1月23日
 */
public class PresenceRosterSubscribe2ItemProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.jid(), new IQ().setId(UUID.randomUUID().toString()).add(new Roster(new GroupItem(super.ourRelation(context.jid(), super.build(protocol.getTo())).cast(RosterRelation.class)))).setType(ProtocolType.SET));
		return true;
	}
}
