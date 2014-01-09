package com.sissi.offline.impl;

import java.util.Map;

import com.sissi.protocol.Element;
import com.sissi.protocol.offline.Delay;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-15
 */
public class DelayPresence extends DelayProtocol {

	@Override
	public Map<String, Object> write(Element element) {
		return super.based(element);
	}

	@Override
	public Element read(Map<String, Object> element) {
		Presence presence = (Presence) super.based(element, new Presence());
		return presence.setDelay(new Delay(super.getOffline(), presence.getFrom(), element.get("delay").toString()));
	}

	public Boolean isSupport(Element element) {
		return Presence.class.isAssignableFrom(element.getClass()) && this.isAcceptStatus(element);
	}

	@Override
	public Boolean isSupport(Map<String, Object> storage) {
		return Presence.class.getSimpleName().equals(storage.get("class"));
	}

	private boolean isAcceptStatus(Element element) {
		Type type = Presence.Type.parse(element.getType());
		return type != Presence.Type.AVAILABLE && type != Presence.Type.UNAVAILABLE;
	}
}
