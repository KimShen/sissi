package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.register.Register;
import com.sissi.ucenter.RegisterContext;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月5日
 */
abstract public class RegisterStoreProcessor implements Input {

	private final RegisterContext vCardRegisterContext;

	private final IQTypeProcessor iqTypeProcessor;

	public RegisterStoreProcessor(RegisterContext vCardRegisterContext, IQTypeProcessor iqTypeProcessor) {
		super();
		this.vCardRegisterContext = vCardRegisterContext;
		this.iqTypeProcessor = iqTypeProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.vCardRegisterContext.register(this.filter(Register.class.cast(protocol))) ? true : this.iqTypeProcessor.input(context, IQ.class.cast(protocol.getParent()).clear().getParent());
	}
	abstract protected Fields filter(Fields vCardFields);
}
