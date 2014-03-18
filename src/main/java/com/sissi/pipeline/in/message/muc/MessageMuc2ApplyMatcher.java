package com.sissi.pipeline.in.message.muc;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.ucenter.muc.MucApplyContext;

/**
 * @author kim 2014年3月18日
 */
public class MessageMuc2ApplyMatcher extends ClassMatcher {

	private final boolean request;

	public MessageMuc2ApplyMatcher(boolean request) {
		super(Message.class);
		this.request = request;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.isApply(protocol.cast(Message.class));
	}

	private boolean isApply(Message message) {
		return message.type(MessageType.NORMAL, MessageType.NONE) && (!message.data(MucApplyContext.MUC_REQUEST_ALLOW) == this.request);
	}
}
