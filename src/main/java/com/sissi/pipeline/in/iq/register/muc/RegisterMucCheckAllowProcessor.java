package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 禁止注册校验(房间配置)
 * 
 * @author kim 2014年3月11日
 */
public class RegisterMucCheckAllowProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(NotAllowed.DETAIL);

	private final RoomBuilder room;

	public RegisterMucCheckAllowProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.room.build(super.build(protocol.parent().getTo())).allowed(context.jid(), RoomConfig.ALLOWREGISTER) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
