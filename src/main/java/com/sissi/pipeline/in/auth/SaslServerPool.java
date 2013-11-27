package com.sissi.pipeline.in.auth;

import javax.security.sasl.SaslServer;

import com.sissi.context.JIDContext;

/**
 * @author kim 2013年11月27日
 */
public interface SaslServerPool {

	public void offer(JIDContext context, SaslServer sasl);
	
	public SaslServer take(JIDContext context);
}
