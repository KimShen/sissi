package com.sissi.pipeline.in.message.decline;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 岗位权限校验
 * 
 * @author kim 2014年3月8日
 */
public class MessageDeclineCheckRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	private final RoomBuilder room;

	public MessageDeclineCheckRelationProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.room.build(super.build(protocol.getTo())).allowed(context.jid(), RoomConfig.AFFILIATIONALLOW) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
