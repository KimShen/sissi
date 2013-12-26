package com.sissi.write;

import java.io.Closeable;
import java.nio.ByteBuffer;

/**
 * @author kim 2013年12月23日
 */
public interface Transfer extends Closeable{

	public Transfer transfer(ByteBuffer bytebuf);

	public void close();
}