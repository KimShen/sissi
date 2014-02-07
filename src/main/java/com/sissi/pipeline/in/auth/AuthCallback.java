package com.sissi.pipeline.in.auth;

import com.sissi.context.JIDContext;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2013-10-24
 */
public interface AuthCallback {

	public boolean auth(Auth auth, JIDContext context);

	public boolean isSupport(String mechanism);
}
