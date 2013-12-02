package com.sissi.offline.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.sissi.context.JID.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Delay;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013-11-15
 */
public class MessageStorage extends ProtocolStorage {

	private final String hit;

	public MessageStorage(String hit, JIDBuilder jidBuilder) {
		super(jidBuilder);
		this.hit = hit;
	}

	@Override
	public Map<String, Object> write(Element element) {
		Message message = Message.class.cast(element);
		Map<String, Object> entity = super.based(message);
		entity.put("body", message.getBody().getText());
		entity.put("delay", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		return entity;
	}

	@Override
	public Message read(Map<String, Object> storage) {
		Message message = (Message) this.based(storage, new Message());
		return message.setBody(new Body(storage.get("body").toString())).setDelay(new Delay(this.hit, message.getFrom(), storage.get("delay").toString()));
	}

	public Boolean isSupport(Element element) {
		return Message.class.isAssignableFrom(element.getClass()) && Message.class.cast(element).hasContent();
	}

	@Override
	public Boolean isSupport(Map<String, Object> storage) {
		return Message.class.getSimpleName().equals(storage.get("class"));
	}
}
