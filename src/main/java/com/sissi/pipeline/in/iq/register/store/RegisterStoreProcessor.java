package com.sissi.pipeline.in.iq.register.store;

import com.sissi.context.JIDContext;
import com.sissi.field.Fields;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;
import com.sissi.ucenter.register.RegisterContext;

/**
 * @author kim 2013年12月5日
 */
abstract class RegisterStoreProcessor implements Input {

	private final RegisterContext registerContext;

	private final Input proxy;

	/**
	 * @param registerContext
	 * @param proxy 提交完成后的回调Input
	 */
	public RegisterStoreProcessor(RegisterContext registerContext, Input proxy) {
		super();
		this.proxy = proxy;
		this.registerContext = registerContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Register register = protocol.cast(Register.class);
		return this.registerContext.register(this.username(register), this.process(register)) ? true : this.proxy.input(context, protocol.parent());
	}

	abstract protected Fields process(Fields fields);

	abstract protected String username(Fields fields);
}
