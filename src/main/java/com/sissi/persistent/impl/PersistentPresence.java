package com.sissi.persistent.impl;

import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.sissi.context.JIDBuilder;
import com.sissi.persistent.PersistentElementBox;
import com.sissi.protocol.Element;
import com.sissi.protocol.offline.Delay;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-11-15
 */
public class PersistentPresence extends PersistentProtocol {

	public PersistentPresence(JIDBuilder jidBuilder, String offline) {
		super(Presence.class, jidBuilder, offline);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> query(Element element) {
		return BasicDBObjectBuilder.start().add(PersistentElementBox.fieldFrom, element.getFrom()).add(PersistentElementBox.fieldTo, element.getTo()).add(PersistentElementBox.fieldType, element.getType()).get().toMap();
	}

	@Override
	public Element read(Map<String, Object> element) {
		Presence presence = Presence.class.cast(super.read(element, new Presence()));
		return presence.setDelay(new Delay(super.title, presence.getFrom(), element.get(PersistentElementBox.fieldDelay).toString()));
	}

	public boolean isSupport(Element element) {
		return super.isSupport(element) && !PresenceType.parse(element.getType()).in(PresenceType.AVAILABLE, PresenceType.UNAVAILABLE);
	}
}
