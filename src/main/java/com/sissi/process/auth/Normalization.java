package com.sissi.process.auth;

import com.sissi.protocol.auth.Auth;

/**
 * @author kim 2013-10-24
 */
public interface Normalization {

	public User normalize(Auth auth);

	public Boolean isSupport(String mechanism);
}
