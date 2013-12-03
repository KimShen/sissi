package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.login.Password;
import com.sissi.protocol.iq.login.Register;

/**
 * @author kim 2013年12月3日
 */
public class RegisterPasswordVerifyProcessor implements Input {

	private IQTypeProcessor iqTypeProcessor;

	public RegisterPasswordVerifyProcessor(IQTypeProcessor iqTypeProcessor) {
		super();
		this.iqTypeProcessor = iqTypeProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Password password = Register.class.cast(protocol).findField(Password.class);
		return password != null && password.getText() != null && !password.getText().isEmpty() ? true : this.iqTypeProcessor.input(context, protocol);
	}
}
