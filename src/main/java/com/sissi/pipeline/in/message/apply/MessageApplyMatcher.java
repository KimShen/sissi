package com.sissi.pipeline.in.message.apply;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * 匹配Message.type(normal, none), 匹配XData.type, 匹配XData包含指定XField(可选)
 * 
 * @author kim 2014年3月18日
 */
public class MessageApplyMatcher extends ClassMatcher {

	private final String type;

	private final String node;

	/**
	 * @param type XDate.type
	 */
	public MessageApplyMatcher(String type) {
		this(type, null);
	}

	/**
	 * @param type
	 * @param node 是否包含指定XField
	 */
	public MessageApplyMatcher(String type, String node) {
		super(Message.class);
		this.type = type;
		this.node = node;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.support(protocol.cast(Message.class));
	}

	private boolean support(Message message) {
		return message.type(MessageType.NORMAL, MessageType.NONE) && message.dataType(this.type) && (this.node != null ? message.data(this.node) : true);
	}
}
