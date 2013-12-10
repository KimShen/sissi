package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.register.Register;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.RegisterContext.FieldFinder;
import com.sissi.ucenter.RegisterContext.Fields;

/**
 * @author kim 2013年12月5日
 */
abstract public class RegisterStoreProcessor implements Input {

	private final RegisterContext registerContext;

	private final IQTypeProcessor iqTypeProcessor;

	public RegisterStoreProcessor(RegisterContext registerContext, IQTypeProcessor iqTypeProcessor) {
		super();
		this.registerContext = registerContext;
		this.iqTypeProcessor = iqTypeProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.registerContext.register(this.build(Register.class.cast(protocol))) ? true : this.iqTypeProcessor.input(context, IQ.class.cast(protocol.getParent()).clear().getParent());
	}

	abstract protected Fields build(FieldFinder finder);
}
