package com.sissi.pipeline.in.presence.roster;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-11-4
 */
public class PresenceRosterActionMatcher extends ClassMatcher {

	private final PresenceType type;

	public PresenceRosterActionMatcher(String type) {
		super(Presence.class);
		this.type = PresenceType.parse(type);
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.type.equals(protocol.getType());
	}
}
