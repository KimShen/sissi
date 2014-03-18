package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.muc.MucApplyContext;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2ApplyResponseCheckNicknameSameProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(NotAllowed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XData data = protocol.cast(Message.class).getData();
		return super.ourRelation(super.build(data.findField(MucApplyContext.MUC_JID, XField.class).getValue().toString()), super.build(protocol.parent().getTo())).cast(RelationMuc.class).name(data.findField(MucApplyContext.MUC_NICKNAME, XField.class).getValue().toString(), false) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
