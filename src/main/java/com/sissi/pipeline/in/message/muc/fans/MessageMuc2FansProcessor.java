package com.sissi.pipeline.in.message.muc.fans;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAcceptable;
import com.sissi.ucenter.relation.muc.MucRelationMapping;

/**
 * MUC私人消息转发
 * 
 * @author kim 2014年3月6日
 */
public class MessageMuc2FansProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(NotAcceptable.DETAIL);

	private final MucRelationMapping mapping;

	public MessageMuc2FansProcessor(MucRelationMapping mapping) {
		super();
		this.mapping = mapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		// From = MUC JID
		protocol.setFrom(group.clone().resource(super.ourRelation(context.jid(), group).name()));
		JIDs jids = this.mapping.mapping(group);
		return jids.isEmpty() ? this.writeAndReturn(context, protocol) : this.writeAndReturn(protocol, jids);
	}

	private boolean writeAndReturn(Protocol protocol, JIDs jids) {
		for (JID jid : jids) {
			super.findOne(jid, true).write(protocol);
		}
		return true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
