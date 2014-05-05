package com.sissi.server.exchange;

import java.io.Closeable;

import com.sissi.pipeline.TransferBuffer;

/**
 * Socks交换器
 * 
 * @author kim 2013年12月22日
 */
public interface Exchanger {

	public String host();

	/**
	 * 绑定Socks发起方,用于接收方完毕后显式的关闭发起方
	 * 
	 * @param source
	 * @return
	 */
	public Exchanger source(Closeable source);

	public Exchanger write(TransferBuffer buffer);

	public Exchanger close(Terminal terminal);
}
