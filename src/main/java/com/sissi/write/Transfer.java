package com.sissi.write;

import java.io.Closeable;

/**
 * @author kim 2013年12月23日
 */
public interface Transfer extends Closeable {

	public Transfer transfer(TransferBuffer buffer);

	public void close();
}