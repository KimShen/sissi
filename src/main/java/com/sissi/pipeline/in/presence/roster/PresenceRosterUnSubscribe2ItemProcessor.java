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
 * @author kim 2013-11-18
 */
public class PresenceRosterUnSubscribe2ItemProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.jid(), new IQ().setId(protocol.getParent().getId()).add(new Roster(new GroupItem(RelationRoster.class.cast(super.ourRelation(context.jid(), super.build(protocol.getTo())))))).setType(ProtocolType.SET));
		return true;
	}
}
