package com.sissi.pipeline.in.auth;

import com.sissi.context.JIDContext;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2013-10-24
 */
public interface AuthCallback {

	public Boolean auth(Auth auth, JIDContext context);

	public Boolean isSupport(String mechanism);
}
