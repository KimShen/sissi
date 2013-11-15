package com.sissi.offline.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.offline.StorageBlock;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-15
 */
public abstract class ProtocolStorageBlock implements StorageBlock {

	protected void based(Map<String, Object> block, Protocol protocol) {
		protocol.setId(this.toString(block, "id"));
		protocol.setFrom(this.toString(block, "from"));
		protocol.setTo(this.toString(block, "to"));
		protocol.setType(this.toString(block, "type"));
	}

	protected Map<String, Object> based(Protocol protocol) {
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("id", protocol.getId());
		entity.put("from", protocol.getFrom());
		entity.put("to", protocol.getTo());
		entity.put("type", protocol.getType());
		return entity;
	}

	protected String toString(Map<String, Object> block, String key) {
		Object value = block.get(key);
		return value != null ? value.toString() : null;
	}

}