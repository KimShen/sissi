package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.iq.IQProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.register.Register;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月5日
 */
abstract class RegisterStoreProcessor implements Input {

	private final RegisterContext registerContext;

	private final IQProcessor failedProcessor;

	public RegisterStoreProcessor(RegisterContext registerContext, IQProcessor failedProcessor) {
		super();
		this.registerContext = registerContext;
		this.failedProcessor = failedProcessor;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.registerContext.register(this.process(Register.class.cast(protocol))) ? true : this.failedProcessor.input(context, IQ.class.cast(protocol.getParent()).clear());
	}

	abstract protected Fields process(Fields fields);
}
