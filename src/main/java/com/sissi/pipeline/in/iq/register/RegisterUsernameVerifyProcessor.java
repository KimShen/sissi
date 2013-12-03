package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.iq.IQTypeProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.login.Register;
import com.sissi.protocol.iq.login.Username;

/**
 * @author kim 2013年12月3日
 */
public class RegisterUsernameVerifyProcessor implements Input {

	private IQTypeProcessor iqTypeProcessor;

	public RegisterUsernameVerifyProcessor(IQTypeProcessor iqTypeProcessor) {
		super();
		this.iqTypeProcessor = iqTypeProcessor;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Username username = Register.class.cast(protocol).findField(Username.class);
		return username != null && username.getText() != null && !username.getText().isEmpty() ? true : this.iqTypeProcessor.input(context, protocol);
	}
}
