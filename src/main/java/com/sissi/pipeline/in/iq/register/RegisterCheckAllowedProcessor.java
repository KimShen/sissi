package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.context.impl.OfflineJID;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.register.simple.Registered;
import com.sissi.protocol.iq.register.simple.Username;

/**
 * 校验当前用户是否已登录
 * 
 * @author kim 2014年5月8日
 */
public class RegisterCheckAllowedProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.binding() ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Register.class).clear().add(Registered.FIELD).add(new Username().setText(context.jid().user())).parent().reply().setType(ProtocolType.RESULT).setFrom(OfflineJID.OFFLINE));
		return false;
	}
}
