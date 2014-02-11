package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.muc.Item;
import com.sissi.protocol.presence.muc.ItemAffiliation;
import com.sissi.protocol.presence.muc.ItemRole;
import com.sissi.protocol.presence.muc.XUser;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoinProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.broadcast(context.jid(), super.build("runtime2@group.sissi.pw/test"), Presence.class.cast(protocol).clear().add(new XUser().add(new Item(ItemAffiliation.MEMBER, ItemRole.PARTICIPANT))));
		return false;
	}
}
