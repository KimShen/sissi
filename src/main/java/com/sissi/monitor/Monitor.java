package com.sissi.monitor;

/**
 * @author kim 2014年1月15日
 */
public interface Monitor {

	public final static Long ONE = 1L;

	/**
	 * 
	 * @param key
	 * @param num
	 * @return
	 */
	public Boolean increment(String key, Long num);

	public Boolean decrement(String key, Long num);
}
