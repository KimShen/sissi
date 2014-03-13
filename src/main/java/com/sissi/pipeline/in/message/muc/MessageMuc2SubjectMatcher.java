package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2014年3月6日
 */
public class MessageMuc2SubjectMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public MessageMuc2SubjectMatcher(JIDBuilder jidBuilder) {
		super(Message.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.getTo()).isBare() && this.subject(protocol.cast(Message.class));
	}

	private boolean subject(Message message) {
		return message.subject() && !message.body();
	}
}
