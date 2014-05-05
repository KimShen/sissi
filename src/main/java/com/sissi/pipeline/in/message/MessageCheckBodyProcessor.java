package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * Body有效性校验
 * 
 * @author kim 2013年12月16日
 */
public class MessageCheckBodyProcessor extends ProxyProcessor {

	/*
	 * Body不为空或不为ACK
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Message message = Message.class.cast(protocol);
		return (message.getBody() != null && message.getBody().hasContent()) || message.received();
	}
}