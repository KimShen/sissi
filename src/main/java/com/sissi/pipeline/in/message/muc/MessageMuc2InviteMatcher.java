package com.sissi.pipeline.in.message.muc;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2InviteMatcher extends ClassMatcher {

	public MessageMuc2InviteMatcher() {
		super(Message.class);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.isInvite(protocol.cast(Message.class));
	}

	private boolean isInvite(Message message) {
		return message.type(MessageType.NORMAL, null) && message.isInvite();
	}
}
