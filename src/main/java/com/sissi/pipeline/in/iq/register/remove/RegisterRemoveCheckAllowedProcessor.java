package com.sissi.pipeline.in.iq.register.remove;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;

/**
 * 已登录校验
 * 
 * @author kim 2014年5月9日
 */
public class RegisterRemoveCheckAllowedProcessor implements Input {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(NotAllowed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.binding() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
