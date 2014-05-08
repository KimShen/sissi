package com.sissi.server.exchange;

/**
 * SI追踪器
 * 
 * @author kim 2014年5月8日
 */
public interface TracerContext {

	/**
	 * 追踪记录
	 * 
	 * @param tracer
	 * @return 是否追踪成功
	 */
	public boolean trace(Tracer tracer);

	/**
	 * 更新追踪
	 * 
	 * @param id
	 * @return 追踪是否有效
	 */
	public boolean trace(String id);
}
