package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.MatchClass;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2013-11-4
 */
public class RegisterStoreForkMatcher extends MatchClass {

	private final Boolean isMulti;

	public RegisterStoreForkMatcher(Class<? extends Protocol> clazz, Boolean isMulti) {
		super(clazz);
		this.isMulti = isMulti;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && Register.class.cast(protocol).isMulti() == this.isMulti;
	}
}
