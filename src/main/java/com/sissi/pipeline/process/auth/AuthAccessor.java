package com.sissi.pipeline.process.auth;


/**
 * @author kim 2013-10-24
 */
public interface AuthAccessor {

	public boolean access(AuthCertificate user);
}
