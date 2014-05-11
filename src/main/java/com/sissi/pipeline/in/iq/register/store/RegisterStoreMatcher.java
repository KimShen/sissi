package com.sissi.pipeline.in.iq.register.store;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * 匹配表单类型(复杂表单/简易表单)
 * 
 * @author kim 2013-11-4
 */
public class RegisterStoreMatcher extends ClassMatcher {

	private final boolean multi;

	/**
	 * @param multi 是否为复杂表单
	 */
	public RegisterStoreMatcher(boolean multi) {
		super(Register.class);
		this.multi = multi;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Register.class).form(this.multi);
	}
}
