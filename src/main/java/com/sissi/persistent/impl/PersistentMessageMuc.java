package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.offline.Delay;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2013-11-15
 */
public class PersistentMessageMuc extends PersistentMessage {

	private final String fieldSource = "source";

	private final MucConfigBuilder mucConfigBuilder;

	private final RelationMucMapping relationMucMapping;

	public PersistentMessageMuc(RelationMucMapping relationMucMapping, MucConfigBuilder mucConfigBuilder, JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip, true);
		this.mucConfigBuilder = mucConfigBuilder;
		this.relationMucMapping = relationMucMapping;
	}

	protected boolean isSupportMessage(Message message) {
		return message.body() && message.type(MessageType.GROUPCHAT);
	}

	protected Delay delay(JID jid, Map<String, Object> element, Message message) {
		JID group = super.jidBuilder.build(element.get(MongoConfig.FIELD_TO).toString());
		return new Delay(super.tip, this.mucConfigBuilder.build(group).allowed(jid, MucConfig.HIDDEN_COMPUTER, group) ? null : element.get(this.fieldSource).toString(), element.get(PersistentElementBox.fieldDelay).toString());
	}

	@Override
	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		Message message = Message.class.cast(element);
		if (message.delay()) {
			entity.put(this.fieldSource, message.getDelay().getFrom());
			entity.put(PersistentElementBox.fieldDelay, message.getDelay().getStamp());
			entity.put(MongoConfig.FIELD_FROM, super.jidBuilder.build(element.getFrom()).asStringWithBare());
		} else {
			entity.put(this.fieldSource, this.relationMucMapping.mapping(super.jidBuilder.build(element.getFrom())).jid().asString());
		}
		return entity;
	}

	public Message read(Map<String, Object> element) {
		return super.read(element).noneThread();
	}

	public boolean isSupport(Map<String, Object> storage) {
		return super.isSupport(storage) && MessageType.GROUPCHAT.equals(super.toString(storage, MongoConfig.FIELD_TYPE));
	}
}
