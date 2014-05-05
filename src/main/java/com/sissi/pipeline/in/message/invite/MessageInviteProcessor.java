package com.sissi.pipeline.in.message.invite;

import com.sissi.config.Dictionary;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.Invite;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;

/**
 * Invite.To转发
 * 
 * @author kim 2014年3月8日
 */
public class MessageInviteProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	public MessageInviteProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Invite invite = protocol.cast(Message.class).getMuc().password(this.room.build(super.build(protocol.parent().getTo())).pull(Dictionary.FIELD_PASSWORD, String.class)).getInvite();
		super.findOne(super.build(invite.getTo()), true).write(this.prepareAndReturn(context, protocol.cast(Message.class), invite).reply());
		return true;
	}

	private Message prepareAndReturn(JIDContext context, Message message, Invite invite) {
		invite.setFrom(context.jid().asString());
		return message.noneThread();
	}
}
