package com.sissi.offline;

import java.util.Map;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-15
 */
public interface StorageBlock {

	public Map<String, Object> write(Protocol protocol);

	public Protocol read(Map<String, Object> block);

	public Boolean isSupport(Protocol protocol);

	public Boolean isSupport(Map<String, Object> block);
}
