package com.sissi.pipeline.in.message.muc.fans;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * Message.type(chat, normal)
 * 
 * @author kim 2014年3月6日
 */
public class MessageMuc2FansCheckTypeProcessor implements Input {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Message.class).type(MessageType.CHAT, MessageType.NORMAL) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
