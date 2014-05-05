package com.sissi.pipeline.in.iq.roster.remove;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * 当前JID所有资源向好友推送离席消息
 * 
 * @author kim 2014年2月8日
 */
public class RosterRemove2FansPresenceProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence().type(PresenceType.UNAVAILABLE);
		JID to = super.build(protocol.cast(Roster.class).getFirstItem().getJid());
		for (JID resource : super.resources(context.jid())) {
			super.broadcast(to, presence.setFrom(resource));
		}
		return true;
	}
}
