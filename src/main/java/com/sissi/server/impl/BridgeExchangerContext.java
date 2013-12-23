package com.sissi.server.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sissi.server.Exchanger;
import com.sissi.server.ExchangerContext;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchangerContext implements ExchangerContext {

	private final Map<String, Exchanger> cached = new ConcurrentHashMap<String, Exchanger>();

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
	public Boolean isTarget(String host) {
		return !this.cached.containsKey(host);
	}
}
