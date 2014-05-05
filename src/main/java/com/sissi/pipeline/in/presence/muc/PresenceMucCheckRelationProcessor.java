package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.ucenter.relation.muc.MucRelationMapping;

/**
 * 房间关系校验(离线时校验)
 * 
 * @author kim 2014年3月5日
 */
public class PresenceMucCheckRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("407").type(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	private final MucRelationMapping mapping;

	public PresenceMucCheckRelationProcessor(MucRelationMapping mapping) {
		super();
		this.mapping = mapping;
	}

	/*
	 * MUC JID是否在线
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.mapping.mapping(super.build(protocol.getTo())).same(context.jid()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
