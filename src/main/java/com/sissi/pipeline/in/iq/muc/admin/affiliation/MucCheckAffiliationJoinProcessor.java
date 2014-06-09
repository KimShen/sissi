package com.sissi.pipeline.in.iq.muc.admin.affiliation;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;

/**
 * 是否已加入MUC校验(Get)
 * 
 * @author kim 2014年3月14日
 */
public class MucCheckAffiliationJoinProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.AUTH).add(Forbidden.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).activate() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
