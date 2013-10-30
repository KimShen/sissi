package com.sisi.process.auth;

import com.sisi.protocol.auth.Auth;

/**
 * @author kim 2013-10-24
 */
public interface UserNormalization {

	public User normalize(Auth auth);

	public Boolean isSupport(String mechanism);
}
