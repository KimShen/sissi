package com.sissi.pipeline.in.auth;

import com.sissi.context.JIDContext;
import com.sissi.protocol.iq.auth.Auth;
import com.sissi.ucenter.access.AuthMechanism;

/**
 * SASL回调
 * 
 * @author kim 2013-10-24
 */
public interface AuthCallback extends AuthMechanism {

	public boolean auth(Auth auth, JIDContext context);
}
