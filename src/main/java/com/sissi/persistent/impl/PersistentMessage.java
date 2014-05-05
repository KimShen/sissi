package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.offline.Delay;

/**
 * Delay Message</p>索引策略:{"id":1}
 * 
 * @author kim 2013-11-15
 */
public class PersistentMessage extends PersistentProtocol {

	/**
	 * bare = true
	 * 
	 * @param jidBuilder
	 * @param tip Delay.tip
	 */
	public PersistentMessage(JIDBuilder jidBuilder, String tip) {
		super(Message.class, jidBuilder, tip, true);
	}

	protected PersistentMessage(JIDBuilder jidBuilder, String tip, boolean bare) {
		super(Message.class, jidBuilder, tip, bare);
	}

	/**
	 * Delay.from = message.from, Delay.stamp = element.delay
	 * 
	 * @param element
	 * @param message
	 * @return
	 */
	protected Delay delay(Map<String, Object> element, Message message) {
		return new Delay(super.tip, message.getFrom(), element.get(Dictionary.FIELD_DELAY).toString());
	}

	/*
	 * {"ack",!message.request(),"thread":message.thread(),"body":message.hasContent() ? message.getBody().getText() : null}
	 * 
	 * @see com.sissi.persistent.impl.PersistentProtocol#write(com.sissi.protocol.Element)
	 */
	@Override
	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		Message message = Message.class.cast(element);
		entity.put(Dictionary.FIELD_ACK, !message.request());
		entity.put(Dictionary.FIELD_THREAD, message.thread());
		entity.put(Dictionary.FIELD_BODY, message.hasContent() ? message.getBody().getText() : null);
		return entity;
	}

	/*
	 * Body, Delay, Thread, Request
	 * 
	 * @see com.sissi.persistent.PersistentElement#read(java.util.Map)
	 */
	@Override
	public Message read(Map<String, Object> element) {
		Message message = Message.class.cast(super.read(element, new Message()));
		return message.body(super.toString(element, Dictionary.FIELD_BODY)).setDelay(this.delay(element, message)).setThread(super.toString(element, Dictionary.FIELD_THREAD)).request(Boolean.valueOf(element.get(Dictionary.FIELD_ACK).toString()));
	}

	/*
	 * 1, 符合Class 2, 符合isSupportMessage
	 * 
	 * @see com.sissi.persistent.impl.PersistentProtocol#isSupport(com.sissi.protocol.Element)
	 */
	public boolean isSupport(Element element) {
		return super.isSupport(element) && this.isSupportMessage(Message.class.cast(element));
	}

	/**
	 * 1, 含Body 2,Mesage.type(CHAT) 3,!Message.received()
	 * 
	 * @param message
	 * @return
	 */
	protected boolean isSupportMessage(Message message) {
		return message.body() && message.type(MessageType.CHAT) && !message.received();
	}

	public Class<? extends Element> support() {
		return Message.class;
	}
}
