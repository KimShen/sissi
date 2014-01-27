package com.sissi.write;

/**
 * @author kim 2014年1月27日
 */
public interface TransferBuffer {
	
	public Object getBuffer();
	
	public TransferBuffer release();
}
