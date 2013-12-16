package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013年12月16日
 */
public class MessageNoneBodyProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Body body = Message.class.cast(protocol).getBody();
		return body != null && body.hasContent();
	}
}