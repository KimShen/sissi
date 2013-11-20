package com.sissi.offline.impl;

import java.util.Map;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-15
 */
public class PresenceStorageBlock extends ProtocolStorageBlock {

	@Override
	public Map<String, Object> write(Protocol protocol) {
		Presence presence = Presence.class.cast(protocol);
		return super.based(presence);
	}

	@Override
	public Protocol read(Map<String, Object> block) {
		try {
			Presence presence = new Presence();
			this.based(block, presence);
			return presence;
		} catch (Exception e) {
			return null;
		}
	}

	public Boolean isSupport(Protocol protocol) {
		return Presence.class.isAssignableFrom(protocol.getClass());
	}

	@Override
	public Boolean isSupport(Map<String, Object> block) {
		return Presence.class.getSimpleName().equals(block.get("clazz"));
	}
}
