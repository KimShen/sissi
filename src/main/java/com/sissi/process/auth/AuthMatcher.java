package com.sissi.process.auth;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.auth.Auth;

/**
 * @author kim 2013-11-4
 */
public class AuthMatcher implements Matcher {

	public Boolean match(Protocol protocol) {
		return Auth.class.isAssignableFrom(protocol.getClass());
	}
}
