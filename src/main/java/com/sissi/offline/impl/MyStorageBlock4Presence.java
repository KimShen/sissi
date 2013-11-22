package com.sissi.offline.impl;

import java.util.Map;

import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-15
 */
public class MyStorageBlock4Presence extends MyStorageBlock4Protocol {

	public MyStorageBlock4Presence(JIDBuilder jidBuilder) {
		super(jidBuilder);
	}

	@Override
	public Map<String, Object> write(Protocol protocol) {
		return super.based(Presence.class.cast(protocol));
	}

	@Override
	public Protocol read(Map<String, Object> block) {
		return this.based(block, new Presence());
	}

	public Boolean isSupport(Protocol protocol) {
		return Presence.class.isAssignableFrom(protocol.getClass()) && this.isNotOnlinePresence(protocol);
	}

	@Override
	public Boolean isSupport(Map<String, Object> block) {
		return Presence.class.getSimpleName().equals(block.get("class"));
	}

	private boolean isNotOnlinePresence(Protocol protocol) {
		return Presence.Type.parse(protocol.getType()) != Presence.Type.ONLINE;
	}

}
