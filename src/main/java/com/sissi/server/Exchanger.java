package com.sissi.server;

import java.io.Closeable;

import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月22日
 */
public interface Exchanger {

	public Exchanger source(Closeable source);

	public Exchanger write(TransferBuffer buffer);

	public Exchanger close(ExchangerPoint point);
}
