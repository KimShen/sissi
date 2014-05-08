package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.register.simple.Username;

/**
 * 校验当前用户是否已登录
 * 
 * @author kim 2014年5月8日
 */
public class RegisterCheckMultiUsernameProcessor implements Input {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XField username = protocol.cast(Register.class).findField(XData.NAME, XData.class).findField(Username.NAME, XField.class);
		return username != null && username.getValue() != null && !username.getValue().toString().isEmpty() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
