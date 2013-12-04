package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.register.Register;
import com.sissi.ucenter.RegisterContext;

/**
 * @author kim 2013年12月3日
 */
public class RegisterStoreProcessor implements Input {

	private RegisterContext registerContext;

	private IQTypeProcessor iqTypeProcessor;

	public RegisterStoreProcessor(RegisterContext registerContext, IQTypeProcessor iqTypeProcessor) {
		super();
		this.registerContext = registerContext;
		this.iqTypeProcessor = iqTypeProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.registerContext.register(Register.class.cast(protocol).getFields()) ? true : this.iqTypeProcessor.input(context, IQ.class.cast(protocol.getParent()).getParent());
	}
}
