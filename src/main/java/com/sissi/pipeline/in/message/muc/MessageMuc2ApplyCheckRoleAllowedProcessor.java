package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2ApplyCheckRoleAllowedProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(NotAllowed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return ItemRole.parse(super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(RelationMuc.class).role()).contains(ItemRole.MODERATOR) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
