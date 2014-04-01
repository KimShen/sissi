package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013年12月16日
 */
public class MessageCheckBodyProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Message message = Message.class.cast(protocol);
		return (message.getBody() != null && message.getBody().hasContent()) || message.received() || message.readed();
	}
}