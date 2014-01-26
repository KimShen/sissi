package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月23日
 */
public class PresenceRosterSubscribe2ItemProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.getJid(), new IQ().add(new Roster(new GroupItem(RelationRoster.class.cast(super.ourRelation(context.getJid(), super.build(protocol.getTo())))))).setType(ProtocolType.SET));
		return true;
	}
}
