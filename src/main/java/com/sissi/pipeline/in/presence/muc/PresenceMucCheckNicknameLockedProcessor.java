package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAcceptable;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 昵称锁定校验(房间配置)</p>TODO: 相关ConfigApprover未实现
 * 
 * @author kim 2014年3月7日
 */
public class PresenceMucCheckNicknameLockedProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("409").setType(ProtocolType.CANCEL).add(NotAcceptable.DETAIL);

	private final RoomBuilder room;

	public PresenceMucCheckNicknameLockedProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		return this.room.build(group).allowed(context.jid(), RoomConfig.CHANGENICK, group.resource()) ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
