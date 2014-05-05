package com.sissi.pipeline;

/**
 * @author kim 2013年12月23日
 */
public interface TransferParam {

	public final static String KEY_SI = "si";

	public final static String KEY_BARE = "bare";

	public <T> T find(String key, Class<T> clazz);
}
