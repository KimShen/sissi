package com.sissi.pipeline.in.presence.roster;

import com.sissi.context.JID;
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
public class PresenceRosterUnSubscribed2ItemProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID source = super.build(protocol.getTo());
		super.broadcast(source, new IQ().setId(protocol.getParent().getId()).add(new Roster(new GroupItem(RelationRoster.class.cast(super.ourRelation(source, context.jid()))))).setType(ProtocolType.SET));
		return true;
	}
}
