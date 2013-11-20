package com.sissi.pipeline.process.message.p2p;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013-10-24
 */
public class MessageP2PRouteProcessor implements ProcessPipeline {

	private JIDBuilder jidBuilder;

	private Addressing addressing;

	public MessageP2PRouteProcessor(JIDBuilder jidBuilder, Addressing addressing) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		this.processTextMessage(context, Message.class.cast(protocol));
		return true;
	}

	private void processTextMessage(JIDContext context, Message message) {
		message.setFrom(context.jid().asString());
		this.addressing.find(this.jidBuilder.build(message.getTo())).write(message);
	}
}
