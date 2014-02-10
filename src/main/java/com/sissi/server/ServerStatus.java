package com.sissi.server;

/**
 * @author kim 2014年2月10日
 */
public interface ServerStatus {

	public final static String STATUS_STARTED = "started";

	public ServerStatus offer(String key, Object value);

	public <T> T take(String key, Class<T> clazz);
}
