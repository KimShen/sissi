package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.offline.Delay;
import com.sissi.ucenter.relation.muc.MucRelationMapping;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * Message MUC消息
 * 
 * @author kim 2013-11-15
 */
public class PersistentMessageMuc extends PersistentMessage {

	private final RoomBuilder room;

	private final MucRelationMapping relationMucMapping;

	public PersistentMessageMuc(MucRelationMapping relationMucMapping, RoomBuilder room, JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip, false);
		this.room = room;
		this.relationMucMapping = relationMucMapping;
	}

	/**
	 * MUC whois相关
	 * 
	 * @param jid
	 * @param element
	 * @param message
	 * @return
	 */
	protected Delay delay(JID jid, Map<String, Object> element, Message message) {
		JID group = super.jidBuilder.build(element.get(Dictionary.FIELD_TO).toString());
		return new Delay(super.tip, this.room.build(group).allowed(jid, RoomConfig.WHOISALLOW, group) ? null : element.get(Dictionary.FIELD_SOURCE).toString(), element.get(Dictionary.FIELD_DELAY).toString());
	}

	/*
	 * 如果包含Message.delay则使用Delay信息, {"source":message.delay.from,"delay":message.delay.stamp,"from":element.from.bare}</p>否则使用MUC JID对应真实JID作为source
	 * 
	 * @see com.sissi.persistent.impl.PersistentMessage#write(com.sissi.protocol.Element)
	 */
	@Override
	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		Message message = Message.class.cast(element);
		if (message.delay()) {
			entity.put(Dictionary.FIELD_SOURCE, message.getDelay().getFrom());
			entity.put(Dictionary.FIELD_DELAY, message.getDelay().getStamp());
			entity.put(Dictionary.FIELD_FROM, super.jidBuilder.build(element.getFrom()).asStringWithBare());
		} else {
			entity.put(Dictionary.FIELD_SOURCE, this.relationMucMapping.mapping(super.jidBuilder.build(element.getFrom())).jid().asString());
		}
		return entity;
	}

	/*
	 * 忽略Thread
	 * 
	 * @see com.sissi.persistent.impl.PersistentMessage#read(java.util.Map)
	 */
	public Message read(Map<String, Object> element) {
		return super.read(element).noneThread();
	}

	/*
	 * Super.isSupport and type = groupchat
	 * 
	 * @see com.sissi.persistent.impl.PersistentProtocol#isSupport(java.util.Map)
	 */
	public boolean isSupport(Map<String, Object> element) {
		return super.isSupport(element) && MessageType.GROUPCHAT.equals(super.toString(element, Dictionary.FIELD_TYPE));
	}

	/*
	 * Message.body && type = groupchat
	 * 
	 * @see com.sissi.persistent.impl.PersistentMessage#isSupportMessage(com.sissi.protocol.message.Message)
	 */
	protected boolean isSupportMessage(Message message) {
		return message.body() && message.type(MessageType.GROUPCHAT);
	}
}
