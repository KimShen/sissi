package com.sissi.pipeline.in.iq.register.store;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.iq.register.RegisterCheckAllowedProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.register.simple.Username;

/**
 * 校验当前用户是否已登录或当前请求为修改密码
 * 
 * @author kim 2014年5月9日
 */
public class RegisterStoreCheckAllowedProcessor extends RegisterCheckAllowedProcessor {

	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Register.class).findField(Username.NAME, Username.class).getValue().equals(context.jid().user()) ? true : super.input(context, protocol);
	}

}
