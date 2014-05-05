package com.sissi.pipeline;

/**
 * 数据块(Block)
 * 
 * @author kim 2014年1月27日
 */
public interface TransferBuffer {

	/**
	 * 底层数据
	 * 
	 * @return
	 */
	public Object getBuffer();

	/**
	 * 释放资源
	 * 
	 * @return
	 */
	public TransferBuffer release();
}
