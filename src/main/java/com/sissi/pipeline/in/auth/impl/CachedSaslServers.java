package com.sissi.pipeline.in.auth.impl;

import java.util.Map;
import java.util.WeakHashMap;

import javax.security.sasl.SaslServer;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.auth.SaslServers;

/**
 * @author kim 2013年11月27日
 */
public class CachedSaslServers implements SaslServers {

	private final Map<JIDContext, SaslServer> cached = new WeakHashMap<JIDContext, SaslServer>();

	@Override
	public SaslServer set(JIDContext context, SaslServer sasl) {
		this.cached.put(context, sasl);
		return sasl;
	}

	@Override
	public SaslServer get(JIDContext context) {
		return this.cached.remove(context);
	}
}
