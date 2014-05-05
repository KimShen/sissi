package com.sissi.pipeline.in.message.muc.fans;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * 匹配包含资源的MUC JID && Message.hasContent
 * 
 * @author kim 2014年3月6日
 */
public class MessageMuc2FansMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public MessageMuc2FansMatcher(JIDBuilder jidBuilder) {
		super(Message.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.support(this.jidBuilder.build(protocol.getTo())) && protocol.cast(Message.class).hasContent();
	}

	private boolean support(JID jid) {
		return jid.isGroup() && !jid.isBare();
	}
}
