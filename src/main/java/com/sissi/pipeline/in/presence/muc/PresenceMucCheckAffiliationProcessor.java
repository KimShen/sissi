package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.RegistrationRequired;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 岗位校验
 * 
 * @author kim 2014年2月22日
 */
public class PresenceMucCheckAffiliationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("407").setType(ProtocolType.AUTH).add(RegistrationRequired.DETAIL);

	private final RoomBuilder room;

	public PresenceMucCheckAffiliationProcessor(RoomBuilder room) {
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
