package com.sissi.persistent.impl;

import java.util.Map;

import com.sissi.config.MongoConfig;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.Element;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.Invite;
import com.sissi.protocol.muc.XUser;

/**
 * @author kim 2014年3月31日
 */
public class PersistentMessageInvite extends PersistentMessage {

	private final String invite = "invite";

	private final String reason = "reason";

	private final String continued = "continue";

	public PersistentMessageInvite(JIDBuilder jidBuilder, String tip) {
		super(jidBuilder, tip);
	}

	protected boolean isSupportMessage(Message message) {
		return message.invite();
	}

	public Map<String, Object> write(Element element) {
		Map<String, Object> entity = super.write(element);
		Invite invite = Message.class.cast(element).getUser().getInvite();
		entity.put(this.invite, invite.getFrom());
		entity.put(this.reason, invite.reason());
		entity.put(this.continued, invite.continued());
		entity.put(MongoConfig.FIELD_TYPE, this.invite);
		return entity;
	}

	@Override
	public Message read(Map<String, Object> element) {
		Message message = Message.class.cast(super.read(element, new Message()));
		return message.setUser(new XUser().invite(new Invite().reason(super.toString(element, this.reason)).continued(super.toString(element, this.continued)).setFrom(element.get(this.invite).toString()))).setDelay(super.delay(element, message)).request(Boolean.getBoolean(element.get(PersistentElementBox.fieldAck).toString()));
	}
}
