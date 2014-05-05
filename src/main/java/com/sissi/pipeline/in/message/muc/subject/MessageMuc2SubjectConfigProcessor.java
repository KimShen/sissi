package com.sissi.pipeline.in.message.muc.subject;

import com.sissi.config.Dictionary;
import com.sissi.context.JIDContext;
import com.sissi.field.impl.BeanField;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;

/**
 * 更新主题
 * 
 * @author kim 2014年3月13日
 */
public class MessageMuc2SubjectConfigProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	public MessageMuc2SubjectConfigProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.room.build(super.build(protocol.getTo())).push(new BeanField<Object>().name(Dictionary.FIELD_SUBJECT).value(protocol.cast(Message.class).getSubject().config()));
		return true;
	}
}
