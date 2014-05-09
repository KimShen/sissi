package com.sissi.pipeline.in.iq.register.remove;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.register.RegisterContext;

/**
 * 注销
 * 
 * @author kim 2014年5月9日
 */
public class RegisterRemoveProcessor implements Input {

	private final RegisterContext registerContext;

	public RegisterRemoveProcessor(RegisterContext registerContext) {
		super();
		this.registerContext = registerContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.registerContext.unregister(context.jid().user());
	}
}
