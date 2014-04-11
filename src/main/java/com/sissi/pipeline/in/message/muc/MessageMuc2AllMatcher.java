package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2AllMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public MessageMuc2AllMatcher(JIDBuilder jidBuilder) {
		super(Message.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.support(protocol.parent().cast(Message.class)) && this.jidBuilder.build(protocol.getTo()).isBare();
	}

	private boolean support(Message message) {
		return message.type(MessageType.GROUPCHAT) && message.hasContent();
	}
}
