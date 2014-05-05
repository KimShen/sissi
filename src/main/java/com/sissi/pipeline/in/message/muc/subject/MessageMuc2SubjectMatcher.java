package com.sissi.pipeline.in.message.muc.subject;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * 匹配JID.bare && Message.subject && !Message.body
 * 
 * @author kim 2014年3月6日
 */
public class MessageMuc2SubjectMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	private final boolean smart;

	public MessageMuc2SubjectMatcher(JIDBuilder jidBuilder, boolean smart) {
		super(Message.class);
		this.jidBuilder = jidBuilder;
		this.smart = smart;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.getTo()).isBare() && this.subject(protocol.cast(Message.class)) && this.groupchat(protocol);
	}

	private boolean subject(Message message) {
		return message.subject() && !message.body();
	}

	private boolean groupchat(Protocol protocol) {
		if (this.smart) {
			protocol.setType(MessageType.GROUPCHAT.toString());
		}
		return true;
	}
}
