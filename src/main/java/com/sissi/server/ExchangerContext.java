package com.sissi.server;

/**
 * @author kim 2013年12月22日
 */
public interface ExchangerContext {

	public Exchanger get(String host);

	public ExchangerContext set(String host, Exchanger exchanger);

	public Boolean isTarget(String host);
}
