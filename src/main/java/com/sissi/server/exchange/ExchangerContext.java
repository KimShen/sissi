package com.sissi.server.exchange;

import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月22日
 */
public interface ExchangerContext {

	public Exchanger join(String host, Transfer transfer);
	
	public Exchanger active(String host);

	public boolean exists(String host);
}
