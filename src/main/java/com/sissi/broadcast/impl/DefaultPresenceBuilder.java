package com.sissi.broadcast.impl;

import com.sissi.broadcast.PresenceQueue.PresenceBuilder;
import com.sissi.context.JID;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * Default Builder, every input will new
 * @author kim 2013-11-17
 */
public class DefaultPresenceBuilder implements PresenceBuilder {

	@Override
	public Presence build(JID from, JID to, Type type) {
		return new Presence(from, to, type);
	}
}
