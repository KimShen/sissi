package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.register.Password;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.register.Username;

/**
 * @author kim 2013年12月3日
 */
public class RegisterFieldsProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Register.class.cast(protocol).add(Username.FIELD).add(Password.FIELD).getParent().setType(Type.RESULT));
		return true;
	}
}
