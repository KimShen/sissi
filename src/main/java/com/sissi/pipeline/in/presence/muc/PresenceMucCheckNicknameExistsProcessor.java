package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;
import com.sissi.ucenter.relation.muc.MucRelationMapping;

/**
 * 昵称冲突性校验
 * 
 * @author kim 2014年2月11日
 */
public class PresenceMucCheckNicknameExistsProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("409").type(ProtocolType.CANCEL).add(Conflict.DETAIL_ELEMENT);

	private final MucRelationMapping mapping;

	private final boolean multi;

	public PresenceMucCheckNicknameExistsProcessor(boolean multi, MucRelationMapping mapping) {
		super();
		this.mapping = mapping;
		this.multi = multi;
	}

	/*
	 * 昵称存在且不属于当前JID
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JIDs exists = this.mapping.mapping(super.build(protocol.getTo()));
		return exists.isEmpty() || (this.multi ? exists.like(context.jid()) : exists.same(context.jid())) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
