package com.sissi.offline.impl;

import java.util.Map;

import com.mongodb.DBObject;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013-11-15
 */
public class MessageStorageBlock extends ProtocolStorageBlock {

	@Override
	public Map<String, Object> write(Protocol protocol) {
		Message message = Message.class.cast(protocol);
		Map<String, Object> entity = super.based(message);
		entity.put("body", message.getBody().getText());
		return entity;
	}

	@Override
	public Message read(Map<String, Object> block) {
		try {
			Message message = new Message();
			this.based(block, message);
			message.setBody(new Body(((DBObject) block.get("body")).toString()));
			return message;
		} catch (Exception e) {
			return null;
		}
	}

	public Boolean isSupport(Protocol protocol) {
		return Message.class.isAssignableFrom(protocol.getClass());
	}

	@Override
	public Boolean isSupport(Map<String, Object> block) {
		return Message.class.getSimpleName().equals(block.get("clazz"));
	}
}
