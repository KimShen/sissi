package com.sissi.pipeline.in.message.muc.subject;

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
 * 是否允许修改主题校验
 * 
 * @author kim 2014年3月13日
 */
public class MessageMuc2SubjectCheckRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.AUTH).add(Forbidden.DETAIL);

	private final RoomBuilder room;

	public MessageMuc2SubjectCheckRelationProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.room.build(super.build(protocol.getTo())).allowed(context.jid(), RoomConfig.CHANGESUBJECT) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
