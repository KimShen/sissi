package com.sissi.offline.impl;

import java.util.Map;

import com.sissi.context.JID.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-15
 */
public class PresenceStorage extends ProtocolStorage {

	public PresenceStorage(JIDBuilder jidBuilder) {
		super(jidBuilder);
	}

	@Override
	public Map<String, Object> write(Element element) {
		return super.based(Presence.class.cast(element));
	}

	@Override
	public Element read(Map<String, Object> storage) {
		return this.based(storage, new Presence());
	}

	public Boolean isSupport(Element element) {
		return Presence.class.isAssignableFrom(element.getClass()) && this.isNotOnlinePresence(element);
	}

	@Override
	public Boolean isSupport(Map<String, Object> storage) {
		return Presence.class.getSimpleName().equals(storage.get("class"));
	}

	private boolean isNotOnlinePresence(Element element) {
		return Presence.Type.parse(element.getType()) != Presence.Type.ONLINE;
	}

}
