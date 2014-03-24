package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucRegister;
import com.sissi.ucenter.muc.MucRelationContext;

/**
 * @author kim 2014年3月24日
 */
public class RegisterMucStoreBroadcastProcessor extends ProxyProcessor {

	private final MucRelationContext mucRelationContext;

	private final MucRegister mucRegister;

	public RegisterMucStoreBroadcastProcessor(MucRelationContext mucRelationContext, MucRegister mucRegister) {
		super();
		this.mucRelationContext = mucRelationContext;
		this.mucRegister = mucRegister;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Message message = new Message().noneThread().setFrom(protocol.parent().getTo()).cast(Message.class).setData(this.mucRegister.register(protocol.cast(Register.class).findField(XData.NAME, XData.class), new XData().setType(XDataType.FORM)));
		for (Relation relation : this.mucRelationContext.myRelations(super.build(protocol.parent().getTo()), ItemRole.MODERATOR.toString())) {
			super.findOne(super.build(relation.jid()), true).write(message);
		}
		return true;
	}
}
