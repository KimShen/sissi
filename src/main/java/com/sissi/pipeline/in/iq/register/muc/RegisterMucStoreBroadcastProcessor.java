package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XInput;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelationContext;
import com.sissi.ucenter.relation.muc.apply.RequestConfig;
import com.sissi.ucenter.relation.muc.register.RegisterContext;

/**
 * 广播主持人
 * 
 * @author kim 2014年3月24日
 */
public class RegisterMucStoreBroadcastProcessor extends ProxyProcessor {

	private final MucRelationContext relationContext;

	private final RegisterContext register;

	private final String subject;

	public RegisterMucStoreBroadcastProcessor(String subject, MucRelationContext relationContext, RegisterContext register) {
		super();
		this.relationContext = relationContext;
		this.register = register;
		this.subject = subject;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Message message = new Message().noneThread().subject(this.subject).setFrom(protocol.parent().getTo()).cast(Message.class).data(this.register.register(protocol.cast(Register.class).findField(XData.NAME, XData.class), new XData().setType(XDataType.FORM)).add(new XInput(XFieldType.HIDDEN.toString(), null, RequestConfig.GROUP.toString(), protocol.parent().getTo())).add(new XInput(XFieldType.HIDDEN.toString(), null, RequestConfig.JID.toString(), context.jid().asStringWithBare())));
		for (Relation relation : this.relationContext.myRelations(super.build(protocol.parent().getTo()), ItemRole.MODERATOR.toString())) {
			super.findOne(super.build(relation.jid()), true).write(message);
		}
		return true;
	}
}
