package com.sissi.offline.impl;

import java.util.Map;

import com.sissi.protocol.Element;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.offline.Delay;

/**
 * Convert message
 * @author kim 2013-11-15
 */
public class DelayMessage extends DelayProtocol {

	private final String BODY = "body";

	@Override
	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.based(element);
		entity.put(BODY, Message.class.cast(element).getBody().getText());
		return entity;
	}

	@Override
	public Message read(Map<String, Object> element) {
		Message message = Message.class.cast(super.based(element, new Message()));
		return message.setBody(new Body(element.get(BODY).toString())).setDelay(new Delay(super.getOffline(), message.getFrom(), element.get(DELAY).toString()));
	}

	public Boolean isSupport(Element element) {
		return Message.class.isAssignableFrom(element.getClass()) && Message.class.cast(element).hasContent();
	}

	@Override
	public Boolean isSupport(Map<String, Object> element) {
		return Message.class.getSimpleName().equals(element.get(CLASS));
	}
}
