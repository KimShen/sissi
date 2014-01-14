package com.sissi.pipeline.in.presence;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.element.SubscriptionRequired;

/**
 * @author kim 2014年1月14日
 */
public class PresenceCheckRelationProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.getJid(), super.build(protocol.getTo())) != null ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setTo(context.getJid()).setError(new ServerError().setType(Type.CANCEL).add(SubscriptionRequired.DETAIL)));
		return false;
	}
}
