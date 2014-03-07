package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2AllProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		protocol.setFrom(group.resource(super.ourRelation(context.jid(), group).getName()));
		for (JID jid : super.whoSubscribedMe(group)) {
			super.findOne(jid, true).write(protocol);
		}
		return true;
	}
}
