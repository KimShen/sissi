package com.sissi.offline.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Delay;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013-11-15
 */
public class MyStorageBlock4Message extends MyStorageBlock4Protocol {

	private String hit;

	public MyStorageBlock4Message(String hit, JIDBuilder jidBuilder) {
		super(jidBuilder);
		this.hit = hit;
	}

	@Override
	public Map<String, Object> write(Protocol protocol) {
		Message message = Message.class.cast(protocol);
		Map<String, Object> entity = super.based(message);
		entity.put("body", message.getBody().getText());
		entity.put("delay", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(new Date()));
		return entity;
	}

	@Override
	public Message read(Map<String, Object> block) {
		Message message = new Message();
		this.based(block, message);
		message.setBody(new Body(block.get("body").toString()));
		message.setDelay(new Delay(this.hit, message.getFrom(), block.get("delay").toString()));
		return message;
	}

	public Boolean isSupport(Protocol protocol) {
		return Message.class.isAssignableFrom(protocol.getClass()) && Message.class.cast(protocol).hasContent();
	}

	@Override
	public Boolean isSupport(Map<String, Object> block) {
		return Message.class.getSimpleName().equals(block.get("class"));
	}
}
