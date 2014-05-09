package com.sissi.pipeline.in.iq.register.remove;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * 匹配Register.remove且To为Null或服务器域
 * 
 * @author kim 2014年5月9日
 */
public class RegisterRemoveMatcher extends ClassMatcher {

	private final String domain;

	public RegisterRemoveMatcher(String domain) {
		super(Register.class);
		this.domain = domain;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && (!protocol.to() || protocol.to(this.domain)) && protocol.cast(Register.class).remove();
	}
}
