package com.sissi.pipeline.in.iq.muc.admin.role;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.relation.muc.MucRelation;

/**
 * 角色越权校验(Get)
 * 
 * @author kim 2014年3月14日
 */
public class MucCheckRole4SelfProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(Forbidden.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return ItemRole.parse(super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(MucRelation.class).role()).contains(ItemRole.parse(protocol.cast(XMucAdmin.class).first().getRole())) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
