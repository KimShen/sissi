package com.sissi.pipeline.process.auth;

import com.sissi.protocol.auth.Auth;

/**
 * @author kim 2013-10-24
 */
public interface AuthNormalization {

	public AuthCertificate normalize(Auth auth);

	public Boolean isSupport(String mechanism);
}
