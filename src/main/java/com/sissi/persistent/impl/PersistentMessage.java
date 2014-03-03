package com.sissi.persistent.impl;

import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Body;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.offline.Delay;

/**
 * @author kim 2013-11-15
 */
public class PersistentMessage extends PersistentProtocol {

	private final String body = "body";

	public PersistentMessage(JIDBuilder jidBuilder, String tip) {
		super(Message.class, jidBuilder, tip);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> query(Element element) {
		return BasicDBObjectBuilder.start(PersistentElementBox.fieldId, element.getId()).get().toMap();
	}

	@Override
	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		Message message = Message.class.cast(element);
		entity.put(this.body, message.getBody().getText());
		entity.put(PersistentElementBox.fieldAck, message.request());
		return entity;
	}

	@Override
	public Message read(Map<String, Object> element) {
		Message message = Message.class.cast(super.read(element, new Message()));
		return message.setBody(new Body(element.get(this.body).toString())).setDelay(new Delay(super.tip, message.getFrom(), element.get(PersistentElementBox.fieldDelay).toString())).request(Boolean.getBoolean(element.get(PersistentElementBox.fieldAck).toString()));
	}

	public boolean isSupport(Element element) {
		return super.isSupport(element) && this.isSupportMessage(Message.class.cast(element));
	}

	private boolean isSupportMessage(Message message) {
		return message.hasContent() && message.type(MessageType.CHAT, MessageType.GROUPCHAT) && !message.received();
	}

	public Class<? extends Element> support() {
		return Message.class;
	}
}
