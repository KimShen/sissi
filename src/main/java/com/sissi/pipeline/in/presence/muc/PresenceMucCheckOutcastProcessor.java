package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月18日
 */
public class PresenceMucCheckOutcastProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setCode("403").setType(ProtocolType.AUTH).add(Forbidden.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.jid(), super.build(protocol.getTo())).cast(RelationMuc.class).outcast() ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
