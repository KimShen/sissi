package com.sissi.pipeline.in.auth;

import com.sissi.context.JIDContext;
import com.sissi.protocol.iq.auth.Auth;

/**
 * SASL回调
 * 
 * @author kim 2013-10-24
 */
public interface AuthCallback {

	public boolean auth(Auth auth, JIDContext context);

	/**
	 * 是否支持
	 * 
	 * @param mechanism
	 * @return
	 */
	public boolean support(String mechanism);
}
