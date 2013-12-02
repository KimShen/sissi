package com.sissi.pipeline.in.auth.impl;

import java.util.HashMap;

import javax.security.sasl.SaslServer;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.auth.SaslServers;

/**
 * @author kim 2013年11月27日
 */
public class CachedSaslServers extends HashMap<JIDContext, SaslServer> implements SaslServers {

	private static final long serialVersionUID = 1L;

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
