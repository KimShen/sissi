package com.sissi.server;

import java.nio.ByteBuffer;

/**
 * @author kim 2014年1月27日
 */
public interface ExchangerByteBuffer {

	public ByteBuffer buffer();
	
	public ExchangerByteBuffer release();
}
