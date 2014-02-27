package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2013-11-4
 */
public class RegisterStoreMatcher extends ClassMatcher {

	private final boolean form;

	public RegisterStoreMatcher(boolean form) {
		super(Register.class);
		this.form = form;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Register.class).form(this.form);
	}
}
