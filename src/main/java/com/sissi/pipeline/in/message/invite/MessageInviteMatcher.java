package com.sissi.pipeline.in.message.invite;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * 匹配Message.type(normal, none) && message.invite
 * 
 * @author kim 2014年3月8日
 */
public class MessageInviteMatcher extends ClassMatcher {

	public MessageInviteMatcher() {
		super(Message.class);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.isInvite(protocol.cast(Message.class));
	}

	private boolean isInvite(Message message) {
		return message.type(MessageType.NORMAL, MessageType.NONE) && message.invite();
	}
}
