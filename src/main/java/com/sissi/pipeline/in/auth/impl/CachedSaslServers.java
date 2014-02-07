package com.sissi.pipeline.in.auth.impl;

import java.util.Map;
import java.util.WeakHashMap;

import javax.security.sasl.SaslServer;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.auth.SaslServers;
import com.sissi.resource.ResourceCounter;

/**
 * @author kim 2013年11月27日
 */
public class CachedSaslServers implements SaslServers {

	private final Map<JIDContext, SaslServer> cached = new WeakHashMap<JIDContext, SaslServer>();

	private final String resource = this.getClass().getSimpleName();

	private final ResourceCounter resourceCounter;

	public CachedSaslServers(ResourceCounter resourceCounter) {
		super();
		this.resourceCounter = resourceCounter;
	}

	@Override
	public SaslServer push(JIDContext context, SaslServer sasl) {
		this.cached.put(context, sasl);
		this.resourceCounter.increment(this.resource);
		return sasl;
	}

	@Override
	public SaslServer pull(JIDContext context) {
		SaslServer saslServer = this.cached.remove(context);
		this.resourceCounter.decrement(this.resource);
		return saslServer;
	}
}
