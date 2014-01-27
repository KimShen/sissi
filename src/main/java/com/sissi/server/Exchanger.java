package com.sissi.server;

import java.io.Closeable;

import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月22日
 */
public interface Exchanger {

	public Exchanger write(TransferBuffer buffer);

	public Exchanger initer(Closeable initer);

	public Exchanger close(ExchangerCloser closer);
}
