package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2013-11-4
 */
public class RegisterStoreMatcher extends ClassMatcher {

	private final Boolean isForm;

	public RegisterStoreMatcher(Boolean isForm) {
		super(Register.class);
		this.isForm = isForm;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && Register.class.cast(protocol).isForm() == this.isForm;
	}
}
