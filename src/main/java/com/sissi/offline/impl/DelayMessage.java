package com.sissi.offline.impl;

import java.util.Map;

import com.sissi.context.JID.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.offline.Delay;

/**
 * @author kim 2013-11-15
 */
public class DelayMessage extends DelayProtocol {

	public DelayMessage(String hit, JIDBuilder jidBuilder) {
		super(hit, jidBuilder);
	}

	@Override
	public Map<String, Object> write(Element element) {
		Message message = Message.class.cast(element);
		Map<String, Object> entity = super.based(message);
		entity.put("body", message.getBody().getText());
		return entity;
	}

	@Override
	public Message read(Map<String, Object> element) {
		Message message = (Message) super.based(element, new Message());
		return message.setBody(new Body(element.get("body").toString())).setDelay(new Delay(super.hit, message.getFrom(), element.get("delay").toString()));
	}

	public Boolean isSupport(Element element) {
		return Message.class.isAssignableFrom(element.getClass()) && Message.class.cast(element).hasContent();
	}

	@Override
	public Boolean isSupport(Map<String, Object> element) {
		return Message.class.getSimpleName().equals(element.get("class"));
	}
}
