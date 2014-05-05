package com.sissi.pipeline.in.presence.muc;

import com.sissi.config.Dictionary;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.message.Subject;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;

/**
 * 主题
 * 
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfMessageSubjectProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	public PresenceMucJoin2SelfMessageSubjectProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		String subject = room.build(group).pull(Dictionary.FIELD_SUBJECT, String.class);
		if (subject != null) {
			context.write(new Message().noneThread().subject(new Subject(subject)).setType(MessageType.GROUPCHAT).setFrom(group.resource(super.ourRelation(context.jid(), group).name())));
		}
		return true;
	}
}
