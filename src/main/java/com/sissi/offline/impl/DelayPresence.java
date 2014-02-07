package com.sissi.offline.impl;

import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Element;
import com.sissi.protocol.offline.Delay;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-11-15
 */
public class DelayPresence extends DelayProtocol {

	public DelayPresence(JIDBuilder jidBuilder, String offline) {
		super(Presence.class, jidBuilder, offline);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> query(Element element) {
		return BasicDBObjectBuilder.start().add(DelayProtocol.fieldFrom, element.getFrom()).add(DelayProtocol.fieldTo, element.getTo()).add(DelayProtocol.fieldType, element.getType()).get().toMap();
	}

	@Override
	public Element read(Map<String, Object> element) {
		Presence presence = Presence.class.cast(super.read(element, new Presence()));
		return presence.setDelay(new Delay(super.offline, presence.getFrom(), element.get(fieldDelay).toString()));
	}

	public boolean isSupport(Element element) {
		return super.isSupport(element) && !PresenceType.parse(element.getType()).in(PresenceType.AVAILABLE, PresenceType.UNAVAILABLE);
	}
}
