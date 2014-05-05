package com.sissi.context;

/**
 * @author kim 2013年12月23日
 */
public interface JIDContextParam {

	public final static String KEY_OUTPUT = "OUTPUT";

	public final static String KEY_ADDRESS = "ADDRESS";

	public final static String KEY_STARTTLS = "TLS";

	public <T> T find(String key, Class<T> clazz);
}
