package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAcceptable;
import com.sissi.ucenter.muc.MucJIDs;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2FansProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(NotAcceptable.DETAIL);

	private final RelationMucMapping relationMucMapping;

	public MessageMuc2FansProcessor(RelationMucMapping relationMucMapping) {
		super();
		this.relationMucMapping = relationMucMapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		protocol.setFrom(group.clone().resource(super.ourRelation(context.jid(), group).name()));
		MucJIDs jids = this.relationMucMapping.mapping(group);
		return jids.isEmpty() ? this.writeAndReturn(context, protocol) : this.writeAndReturn(protocol, jids);
	}

	private boolean writeAndReturn(Protocol protocol, MucJIDs jids) {
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
