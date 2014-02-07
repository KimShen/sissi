package com.sissi.pipeline.in.auth;

import javax.security.sasl.SaslServer;

import com.sissi.context.JIDContext;

/**
 * @author kim 2013年11月27日
 */
public interface SaslServers {

	public SaslServer push(JIDContext context, SaslServer sasl);

	public SaslServer pull(JIDContext context);
}
