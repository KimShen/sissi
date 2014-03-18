package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.muc.MucApplyContext;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2ApplyResponseCheckRoleNoneProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return ItemRole.NONE.equals(protocol.cast(Message.class).getData().findField(MucApplyContext.MUC_ROLE, XField.class).getValue().toString()) ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
