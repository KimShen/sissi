package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.Invite;
import com.sissi.protocol.muc.XUser;

/**
 * Message MUC邀请</p>索引策略:{"id":1}
 * 
 * @author kim 2014年3月31日
 */
public class PersistentMessageInvite extends PersistentMessage {

	public PersistentMessageInvite(JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip);
	}

	/*
	 * Super.write plus {"invite":invite.from,"reason":invite.reason,"continue":invite.continued,"type":"invite"}
	 * 
	 * @see com.sissi.persistent.impl.PersistentMessage#write(com.sissi.protocol.Element)
	 */
	public Map<String, Object> write(Element element) {
		Invite invite = Message.class.cast(element).getMuc().getInvite();
		Map<String, Object> entity = super.write(element);
		entity.put(Dictionary.FIELD_INVITE, invite.getFrom());
		entity.put(Dictionary.FIELD_REASON, invite.reason());
		entity.put(Dictionary.FIELD_CONTINUE, invite.continued());
		entity.put(Dictionary.FIELD_TYPE, Dictionary.FIELD_INVITE);
		return entity;
	}

	/*
	 * Super.read plus Message.setMuc(XUser.invite(new Invite().reason.continued.from).delay.request
	 * 
	 * @see com.sissi.persistent.impl.PersistentMessage#read(java.util.Map)
	 */
	@Override
	public Message read(Map<String, Object> element) {
		Message message = Message.class.cast(super.read(element, new Message()));
		return message.muc(new XUser().invite(new Invite().reason(super.toString(element, Dictionary.FIELD_REASON)).continued(super.toString(element, Dictionary.FIELD_CONTINUE)).setFrom(element.get(Dictionary.FIELD_INVITE).toString()))).setDelay(super.delay(element, message)).request(Boolean.valueOf(element.get(Dictionary.FIELD_ACK).toString()));
	}

	/*
	 * Super.isSupport plus Element.type = 'invite'
	 * 
	 * @see com.sissi.persistent.impl.PersistentProtocol#isSupport(java.util.Map)
	 */
	public boolean isSupport(Map<String, Object> element) {
		return super.isSupport(element) && Dictionary.FIELD_INVITE.equals(super.toString(element, Dictionary.FIELD_TYPE));
	}

	/*
	 * Message包含Invite
	 * 
	 * @see com.sissi.persistent.impl.PersistentMessage#isSupportMessage(com.sissi.protocol.message.Message)
	 */
	protected boolean isSupportMessage(Message message) {
		return message.invite();
	}
}
