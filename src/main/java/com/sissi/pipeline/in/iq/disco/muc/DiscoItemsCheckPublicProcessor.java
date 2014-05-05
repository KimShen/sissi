package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * MUC公开房间校验
 * 
 * @author kim 2014年3月14日
 */
public class DiscoItemsCheckPublicProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	public DiscoItemsCheckPublicProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.room.build(super.build(protocol.parent().getTo())).allowed(context.jid(), RoomConfig.PUBLICROOM, null) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setType(ProtocolType.RESULT));
		return false;
	}
}
