package com.sissi.pipeline.in.message.muc.all;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 发送显式Delay消息时房间激活校验
 * 
 * @author kim 2014年4月6日
 */
public class MessageMuc2AllCheckDelayProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	private final RoomBuilder room;

	public MessageMuc2AllCheckDelayProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Message.class).delay() ? this.room.build(super.build(protocol.parent().getTo())).allowed(context.jid(), RoomConfig.CONFIGED) ? this.writeAndReturn(context, protocol) : true : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
