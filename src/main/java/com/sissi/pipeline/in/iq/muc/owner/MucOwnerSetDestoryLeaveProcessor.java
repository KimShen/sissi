package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Owner;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年3月24日
 */
public class MucOwnerSetDestoryLeaveProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		Presence presence = new Presence().destory(protocol.cast(Owner.class).getDestory());
		for (JID jid : super.whoSubscribedMe(super.build(protocol.parent().getTo()))) {
			super.findOne(jid, true).write(presence.setFrom(group.resource(super.ourRelation(jid, group).name())));
		}
		return true;
	}
}
