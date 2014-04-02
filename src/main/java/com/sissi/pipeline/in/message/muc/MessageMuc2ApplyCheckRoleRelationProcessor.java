package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.muc.MucApplyContext;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2ApplyCheckRoleRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(NotAllowed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		return ItemRole.parse(super.ourRelation(super.build(protocol.cast(Message.class).getData().findField(MucApplyContext.MUC_JID, XField.class).getValue().toString()), group).cast(RelationMuc.class).role()).contains(super.ourRelation(context.jid(), group).cast(RelationMuc.class).role()) ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
