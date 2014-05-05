package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 非匿名房间警告
 * 
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfMessageWarningProcessor extends ProxyProcessor {

	private final XUser x = new XUser().add("100");

	private final RoomBuilder room;

	private final Body body;

	public PresenceMucJoin2SelfMessageWarningProcessor(RoomBuilder room, String message) {
		super();
		this.room = room;
		this.body = new Body(message);
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		return this.room.build(group).allowed(context.jid(), RoomConfig.WHOISEXISTS) ? true : this.writeAndReturn(context, group);
	}

	private boolean writeAndReturn(JIDContext context, JID group) {
		context.write(new Message().noneThread().body(this.body).muc(this.x).setType(MessageType.GROUPCHAT).setFrom(group.asStringWithBare()));
		return true;
	}
}
