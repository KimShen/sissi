package com.sissi.pipeline.in.iq.register.init;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.register.simple.Redirect;

/**
 * Web注册
 * 
 * @author kim 2014年5月10日
 */
public class RegisterInitRedirectProcessor implements Input {

	private final Redirect redirect;

	public RegisterInitRedirectProcessor(String redirect) {
		super();
		this.redirect = new Redirect(redirect);
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Register.class).clear().add(this.redirect).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
