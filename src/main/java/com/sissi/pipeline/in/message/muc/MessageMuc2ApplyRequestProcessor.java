package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XInput;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucApplyContext;
import com.sissi.ucenter.muc.MucRelationContext;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2ApplyRequestProcessor extends ProxyProcessor {

	private final XInput jid = new XInput(XFieldType.TEXT_SINGLE.toString(), "User ID", MucApplyContext.MUC_JID);

	private final XInput nick = new XInput(XFieldType.TEXT_SINGLE.toString(), "Room Nickname", MucApplyContext.MUC_NICKNAME);

	private final XInput allow = new XInput(XFieldType.BOOLEAN.toString(), "Allow to this person?", MucApplyContext.MUC_REQUEST_ALLOW, "false");

	private final MucRelationContext mucRelationContext;

	public MessageMuc2ApplyRequestProcessor(MucRelationContext mucRelationContext) {
		super();
		this.mucRelationContext = mucRelationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		//需要根据FORM_TYPE区分申请表单,如注册或其他事项
		JID group = super.build(protocol.parent().getTo());
		XData data = protocol.cast(Message.class).noneThread().getData().setType(XDataType.FORM).add(this.jid.clone().value(context.jid().asString())).add(this.nick.clone().value(this.mucRelationContext.ourRelation(context.jid(), group).name())).add(this.allow);
		data.findField(XDataType.FORM_TYPE.toString(), XField.class).setType(XFieldType.HIDDEN);
		for (Relation relation : this.mucRelationContext.myRelations(group, ItemRole.MODERATOR.toString())) {
			super.findOne(super.build(relation.jid()), true).write(protocol.reply());
		}
		return true;
	}
}
