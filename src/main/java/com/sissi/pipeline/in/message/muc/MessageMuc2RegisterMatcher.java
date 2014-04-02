package com.sissi.pipeline.in.message.muc;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.muc.OwnerConfig;

/**
 * @author kim 2014年3月18日
 */
public class MessageMuc2RegisterMatcher extends ClassMatcher {

	public MessageMuc2RegisterMatcher() {
		super(Message.class);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.isApply(protocol.cast(Message.class));
	}

	private boolean isApply(Message message) {
		return message.type(MessageType.NORMAL, MessageType.NONE) && message.data(OwnerConfig.REGISTER_ALLOW.toString());
	}
}
