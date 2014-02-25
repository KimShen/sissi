package com.sissi.pipeline.in.iq.register;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2013-11-4
 */
public class RegisterStoreMatcher extends ClassMatcher {

	private final boolean needForm;

	public RegisterStoreMatcher(boolean needForm) {
		super(Register.class);
		this.needForm = needForm;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Register.class).form(this.needForm);
	}
}
