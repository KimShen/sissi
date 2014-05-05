package com.sissi.pipeline.in.message.muc.all;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * To.resource.bare && type = groupchat && message.hasContent
 * 
 * @author kim 2014年3月6日
 */
public class MessageMuc2AllMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public MessageMuc2AllMatcher(JIDBuilder jidBuilder) {
		super(Message.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.support(this.jidBuilder.build(protocol.getTo())) && this.support(protocol.parent().cast(Message.class));
	}

	private boolean support(JID jid) {
		return jid.isGroup() && jid.isBare();
	}

	private boolean support(Message message) {
		return message.type(MessageType.GROUPCHAT) && message.hasContent();
	}
}
