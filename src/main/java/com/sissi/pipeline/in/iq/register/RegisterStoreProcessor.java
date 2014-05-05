package com.sissi.pipeline.in.iq.register;

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
		return this.registerContext.register(this.process(protocol.cast(Register.class))) ? true : this.proxy.input(context, protocol.parent());
	}

	abstract protected Fields process(Fields fields);
}
