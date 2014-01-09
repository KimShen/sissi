package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.iq.IQProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.register.Register;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月5日
 */
abstract class RegisterStoreProcessor implements Input {

	private final RegisterContext registerContext;

	private final IQProcessor iqProcessor;

	public RegisterStoreProcessor(RegisterContext registerContext, IQProcessor iqProcessor) {
		super();
		this.registerContext = registerContext;
		this.iqProcessor = iqProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.registerContext.register(this.filter(Register.class.cast(protocol))) ? true : this.iqProcessor.input(context, IQ.class.cast(protocol.getParent()).clear());
	}

	abstract protected Fields filter(Fields fields);
}
