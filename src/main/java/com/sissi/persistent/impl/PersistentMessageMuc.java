package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.MongoConfig;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.offline.Delay;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2013-11-15
 */
public class PersistentMessageMuc extends PersistentMessage {

	private final String fieldItem = "item";

	private final RelationMucMapping relationMucMapping;

	public PersistentMessageMuc(RelationMucMapping relationMucMapping, JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip, true);
		this.relationMucMapping = relationMucMapping;
	}

	protected boolean isSupportMessage(Message message) {
		return message.body() && message.type(MessageType.GROUPCHAT);
	}

	protected Delay delay(Map<String, Object> element, Message message) {
		return new Delay(super.tip, element.get(this.fieldItem).toString(), element.get(PersistentElementBox.fieldDelay).toString());
	}

	@Override
	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		entity.put(this.fieldItem, this.relationMucMapping.mapping(super.jidBuilder.build(element.getFrom())).jid().asString());
		return entity;
	}

	public boolean isSupport(Map<String, Object> storage) {
		return super.isSupport(storage) && MessageType.GROUPCHAT.equals(super.toString(storage, MongoConfig.FIELD_TYPE));
	}
}
