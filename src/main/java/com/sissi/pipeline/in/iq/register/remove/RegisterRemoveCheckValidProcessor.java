package com.sissi.pipeline.in.iq.register.remove;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.iq.register.Register;

/**
 * 错误请求校验(禁止同时出现Remove和其他属性)
 * 
 * @author kim 2014年5月9日
 */
public class RegisterRemoveCheckValidProcessor implements Input {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Register.class).valid() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
