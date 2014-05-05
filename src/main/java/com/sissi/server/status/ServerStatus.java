package com.sissi.server.status;

/**
 * 服务器状态
 * 
 * @author kim 2014年2月10日
 */
public interface ServerStatus {

	/**
	 * 服务器启动时间
	 */
	public final static String STATUS_STARTED = "started";

	/**
	 * @param key
	 * @param value new value
	 * @return old value
	 */
	public Object update(String key, Object value);

	public <T> T peek(String key, Class<T> clazz);
}
