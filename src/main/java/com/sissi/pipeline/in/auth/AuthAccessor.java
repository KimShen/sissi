package com.sissi.pipeline.in.auth;


/**
 * @author kim 2013-10-24
 */
public interface AuthAccessor {

	public boolean access(AuthCertificate user);
}
