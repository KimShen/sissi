package com.sissi.pipeline.in.iq.muc.owner.submit;

import com.sissi.config.Dictionary;
import com.sissi.context.JIDContext;
import com.sissi.field.Fields;
import com.sissi.field.impl.BeanField;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Owner;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;

/**
 * 提交配置
 * 
 * @author kim 2014年3月24日
 */
public class MucOwnerSubmitProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	public MucOwnerSubmitProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	/*
	 * 提交并激活
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.room.build(super.build(protocol.parent().getTo())).push(Fields.class.cast(protocol.cast(Owner.class).getX().add(new BeanField<Boolean>().name(Dictionary.FIELD_ACTIVATE).value(true))));
		return true;
	}
}
