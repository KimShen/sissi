package com.sissi.context;

/**
 * @author kim 2013年12月23日
 */
public interface JIDContextParam {

	public <T> T find(String key, Class<T> clazz);
}
