package com.sissi.pipeline.in.iq.muc.admin.role;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;
import com.sissi.protocol.muc.XMucAdmin;

/**
 * 禁止Item列表存在当前MUC JID昵称校验
 * 
 * @author kim 2014年3月14日
 */
public class MucCheckRoleLoopProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(Conflict.DETAIL_ELEMENT);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(XMucAdmin.class).loop(super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).name()) ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
