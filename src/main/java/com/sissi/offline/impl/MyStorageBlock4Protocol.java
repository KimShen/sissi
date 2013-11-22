package com.sissi.offline.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JIDBuilder;
import com.sissi.offline.StorageBlock;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-15
 */
public abstract class MyStorageBlock4Protocol implements StorageBlock {

	private JIDBuilder jidBuilder;

	public MyStorageBlock4Protocol(JIDBuilder jidBuilder) {
		super();
		this.jidBuilder = jidBuilder;
	}

	protected Protocol based(Map<String, Object> block, Protocol protocol) {
		protocol.setId(this.toString(block, "id"));
		protocol.setFrom(this.toString(block, "from"));
		protocol.setTo(this.toString(block, "to"));
		protocol.setType(this.toString(block, "type"));
		return protocol;
	}

	protected Map<String, Object> based(Protocol protocol) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("id", protocol.getId());
		entity.put("from", this.jidBuilder.build(protocol.getFrom()).asStringWithBare());
		entity.put("to", this.jidBuilder.build(protocol.getTo()).asStringWithBare());
		entity.put("type", protocol.getType());
		entity.put("class", protocol.getClass().getSimpleName());
		return entity;
	}

	protected String toString(Map<String, Object> block, String key) {
		Object value = block.get(key);
		return value != null ? value.toString() : null;
	}

}