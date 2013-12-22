package com.sissi.server.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.server.Exchanger;
import com.sissi.server.ExchangerContext;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchangerContext implements ExchangerContext {

	private final Map<String, Exchanger> cached = new HashMap<String, Exchanger>();

	@Override
	public ExchangerContext set(String host, Exchanger exchanger) {
		this.cached.put(host, exchanger);
		return this;
	}

	@Override
	public Exchanger get(String host) {
		return this.cached.remove(host);
	}

	@Override
	public Boolean contains(String host) {
		return this.cached.containsKey(host);
	}
}
