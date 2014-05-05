package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.impl.PresenceWrapRelation;

/**
 * 加入房间
 * 
 * @author kim 2014年2月11日
 */
public class PresenceMucJoinProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		super.establish(context.jid(), new PresenceWrapRelation(group, protocol.cast(Presence.class).findField(XMuc.NAME, XMuc.class), super.ourRelation(context.jid(), group).cast(MucRelation.class)));
		return true;
	}
}
