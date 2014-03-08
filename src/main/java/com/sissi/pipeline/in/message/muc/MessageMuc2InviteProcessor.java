package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XInvite;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2InviteProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XInvite invite = protocol.cast(Message.class).getX().getInvite();
		super.findOne(super.build(invite.getTo()), true).write(this.prepareAndReturn(context, protocol, invite).reply());
		return true;
	}

	private Protocol prepareAndReturn(JIDContext context, Protocol protocol, XInvite invite) {
		invite.setFrom(context.jid().asString());
		return protocol;
	}
}
