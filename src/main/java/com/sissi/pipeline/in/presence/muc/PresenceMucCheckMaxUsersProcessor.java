package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ServiceUnavailable;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 人数校验(线程不安全)
 * 
 * @author kim 2014年3月6日
 */
public class PresenceMucCheckMaxUsersProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("503").type(ProtocolType.WAIT).add(ServiceUnavailable.DETAIL);

	private final RoomBuilder room;

	private final short limit;

	public PresenceMucCheckMaxUsersProcessor(short limit, RoomBuilder room) {
		super();
		this.limit = limit;
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.room.build(super.build(protocol.getTo())).allowed(context.jid(), RoomConfig.MAXUSERS, this.limit) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
