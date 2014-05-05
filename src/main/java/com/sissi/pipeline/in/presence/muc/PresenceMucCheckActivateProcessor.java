package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ItemNotFound;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 激活校验
 * 
 * @author kim 2014年3月6日
 */
public class PresenceMucCheckActivateProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("405").setType(ProtocolType.CANCEL).add(ItemNotFound.DETAIL);

	private final RoomBuilder room;

	public PresenceMucCheckActivateProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.room.build(super.build(protocol.getTo())).allowed(context.jid(), RoomConfig.ACTIVATED) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
