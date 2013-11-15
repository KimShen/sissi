package com.sissi.pipeline.process.message;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013-11-15
 */
public class MessageOfflineProcessor implements ProcessPipeline {

	private JIDBuilder jidBuilder;

	private Addressing addressing;

	public MessageOfflineProcessor(JIDBuilder jidBuilder, Addressing addressing) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		Message message = Message.class.cast(protocol);
		JID to = this.jidBuilder.build(message.getTo());
		return this.addressing.isOnline(to) ? true : false;
	}
}
