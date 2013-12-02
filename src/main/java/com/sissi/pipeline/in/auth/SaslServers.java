package com.sissi.pipeline.in.auth;

import javax.security.sasl.SaslServer;

import com.sissi.context.JIDContext;

/**
 * @author kim 2013年11月27日
 */
public interface SaslServers {

	public SaslServer set(JIDContext context, SaslServer sasl);

	public SaslServer get(JIDContext context);
}
