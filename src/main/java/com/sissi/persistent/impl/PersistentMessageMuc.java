package com.sissi.persistent.impl;

import com.sissi.context.JIDBuilder;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;

/**
 * @author kim 2013-11-15
 */
public class PersistentMessageMuc extends PersistentMessage {

	public PersistentMessageMuc(JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip, true);
	}

	boolean isSupportMessage(Message message) {
		return message.hasContent() && message.type(MessageType.GROUPCHAT);
	}
}
