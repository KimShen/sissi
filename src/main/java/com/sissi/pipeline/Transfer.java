package com.sissi.pipeline;

import java.io.Closeable;

/**
 * 
 * @author kim 2013年12月23日
 */
public interface Transfer extends Closeable {

	/**
	 * 传输
	 * 
	 * @param buffer
	 * @return
	 */
	public Transfer transfer(TransferBuffer buffer);

	public void close();
}