package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.register.Register;

/**
 * 表单校验
 * 
 * @author kim 2014年2月8日
 */
public class RegisterStoreCheckProcessor implements Input {

	private final Error error = new ServerError().setType(ProtocolType.MODIFY).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Register.class).findField(XData.NAME, XData.class).type(XDataType.SUBMIT) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
