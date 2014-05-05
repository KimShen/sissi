package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.message.Message;

/**
 * ACK有效性校验
 * 
 * @author kim 2014年3月3日
 */
public class MessageCheckAckProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.valid(protocol.cast(Message.class)) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean valid(Message message) {
		return message.notConflict() && message.validReceived();
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
