package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.CheckRelationProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.SubscriptionRequired;

/**
 * @author kim 2013-11-18
 */
public class MessageCheckRelationProcessor extends CheckRelationProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(SubscriptionRequired.DETAIL);

	private final boolean isFree;

	public MessageCheckRelationProcessor(boolean isFree) {
		super();
		this.isFree = isFree;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.isFree || context.jid().like(super.build(protocol.getTo())) || super.ourRelation(context, protocol) ? true : this.writeAndReturn(context, protocol);
	}

	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
