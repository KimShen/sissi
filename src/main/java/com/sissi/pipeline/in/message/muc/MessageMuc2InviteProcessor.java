package com.sissi.pipeline.in.message.muc;

import com.sissi.config.MongoConfig;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XInvite;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2InviteProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public MessageMuc2InviteProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XInvite invite = protocol.cast(Message.class).getUser().password(this.mucConfigBuilder.build(super.build(protocol.parent().getTo())).pull(MongoConfig.FIELD_PASSWORD, String.class)).getInvite();
		super.findOne(super.build(invite.getTo()), true).write(this.prepareAndReturn(context, protocol.cast(Message.class), invite).reply());
		return true;
	}

	private Message prepareAndReturn(JIDContext context, Message message, XInvite invite) {
		invite.setFrom(context.jid().asString());
		return message.noneThread();
	}
}
