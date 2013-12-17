package com.sissi.pipeline.in.auth.impl;

import java.util.WeakHashMap;

import javax.security.sasl.SaslServer;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.auth.SaslServers;

/**
 * @author kim 2013年11月27日
 */
public class CachedSaslServers extends WeakHashMap<JIDContext, SaslServer> implements SaslServers {

	@Override
	public SaslServer set(JIDContext context, SaslServer sasl) {
		super.put(context, sasl);
		return sasl;
	}

	@Override
	public SaslServer get(JIDContext context) {
		return super.remove(context);
	}
}
