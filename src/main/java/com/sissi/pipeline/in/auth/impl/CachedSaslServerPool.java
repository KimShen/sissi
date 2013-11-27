package com.sissi.pipeline.in.auth.impl;

import java.util.HashMap;
import java.util.Map;

import javax.security.sasl.SaslServer;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.auth.SaslServerPool;

/**
 * @author kim 2013年11月27日
 */
public class CachedSaslServerPool implements SaslServerPool {

	private Map<JIDContext, SaslServer> cached = new HashMap<JIDContext, SaslServer>();

	@Override
	public void offer(JIDContext context, SaslServer sasl) {
		this.cached.put(context, sasl);
	}

	@Override
	public SaslServer take(JIDContext context) {
		return this.cached.remove(context);
	}

}
