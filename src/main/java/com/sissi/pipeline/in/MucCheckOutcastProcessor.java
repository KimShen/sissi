package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.ucenter.relation.muc.MucRelation;

/**
 * 排斥者校验
 * 
 * @author kim 2014年2月18日
 */
public class MucCheckOutcastProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("403").type(ProtocolType.AUTH).add(Forbidden.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(MucRelation.class).outcast() ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
