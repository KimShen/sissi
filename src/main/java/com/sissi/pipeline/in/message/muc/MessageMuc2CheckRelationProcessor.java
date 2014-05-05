package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAcceptable;

/**
 * 房间关系校验
 * 
 * @author kim 2014年3月10日
 */
public class MessageMuc2CheckRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(NotAcceptable.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.jid(), super.build(protocol.getTo())).activate() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
