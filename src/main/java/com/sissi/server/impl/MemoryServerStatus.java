package com.sissi.server.impl;

import java.util.HashMap;

import com.sissi.server.ServerStatus;

/**
 * @author kim 2014年2月10日
 */
public class MemoryServerStatus extends HashMap<String, Object> implements ServerStatus {

	private static final long serialVersionUID = 1L;

	@Override
	public ServerStatus offer(String key, Object value) {
		super.put(key, value);
		return this;
	}

	@Override
	public <T> T take(String key, Class<T> clazz) {
		return clazz.cast(super.get(key));
	}
}
