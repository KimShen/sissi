package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAuthorized;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 密码校验
 * 
 * @author kim 2014年2月22日
 */
public class PresenceMucCheckPasswordProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("401").type(ProtocolType.AUTH).add(NotAuthorized.DETAIL_ELEMENT);

	private final RoomBuilder room;

	public PresenceMucCheckPasswordProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.room.build(super.build(protocol.getTo())).allowed(context.jid(), RoomConfig.ROOMSECRET, Presence.class.cast(protocol).findField(XMuc.NAME, XMuc.class).passowrd()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
