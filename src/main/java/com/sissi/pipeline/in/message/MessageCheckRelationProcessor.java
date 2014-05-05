package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.CheckRelationProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.SubscriptionRequired;

/**
 * 订阅关系校验
 * 
 * @author kim 2013-11-18
 */
public class MessageCheckRelationProcessor extends CheckRelationProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(SubscriptionRequired.DETAIL);

	public MessageCheckRelationProcessor(boolean shortcut) {
		super(shortcut);
	}

	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
