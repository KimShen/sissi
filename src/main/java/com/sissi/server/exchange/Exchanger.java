package com.sissi.server.exchange;

import java.io.Closeable;

import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月22日
 */
public interface Exchanger {

	public String host();

	public Exchanger source(Closeable source);

	public Exchanger write(TransferBuffer buffer);

	public Exchanger close(ExchangerTerminal terminal);

	public boolean isClose(ExchangerTerminal terminal);
}
