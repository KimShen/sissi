package com.sissi.server;

import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月22日
 */
public interface ExchangerContext {

	public Exchanger set(String host, Transfer transfer);
	
	public Exchanger get(String host);

	public Boolean isTarget(String host);
}
