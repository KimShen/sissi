package com.sissi.pipeline.in.auth;

import com.sissi.context.JIDContext;
import com.sissi.protocol.iq.login.Auth;

/**
 * @author kim 2013-10-24
 */
public interface AuthCallback {

	public Boolean auth(JIDContext context, Auth auth);

	public Boolean isSupport(String mechanism);
}
