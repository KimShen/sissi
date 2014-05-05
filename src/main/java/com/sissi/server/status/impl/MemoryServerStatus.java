package com.sissi.server.status.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.server.status.ServerStatus;

/**
 * 基于内存的服务器状态
 * 
 * @author kim 2014年2月10日
 */
public class MemoryServerStatus implements ServerStatus {

	private final Map<String, Object> status = new HashMap<String, Object>();

	@Override
	public Object update(String key, Object value) {
		return this.status.put(key, value);
	}

	@Override
	public <T> T peek(String key, Class<T> clazz) {
		return clazz.cast(this.status.get(key));
	}
}
