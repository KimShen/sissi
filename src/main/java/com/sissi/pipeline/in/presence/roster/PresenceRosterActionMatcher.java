package com.sissi.pipeline.in.presence.roster;

import com.sissi.pipeline.in.MatchClass;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-4
 */
public class PresenceRosterActionMatcher extends MatchClass {

	private Type type;

	public PresenceRosterActionMatcher(String type) {
		super(Presence.class);
		this.type = Type.parse(type);
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.type.equals(protocol.getType());
	}
}
