package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * Resource重复资源绑定则终止其他JIDContext
 * 
 * @author kim 2013-10-29
 */
public class BindAddressCloseOthersProcessor extends ProxyProcessor {

	private final Body body;

	/**
	 * JIDContext下线提示, Message.type = headline
	 * 
	 * @param content
	 */
	public BindAddressCloseOthersProcessor(String content) {
		super();
		this.body = new Body(content);
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return super.resources(context.jid(), true).isEmpty() ? true : super.findOne(context.jid(), true, true).write(new Message().body(this.body).setType(MessageType.HEADLINE).setFrom(context.domain())).write(Stream.closeGraceFully()).close();
	}
}
