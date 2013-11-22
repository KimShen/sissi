package com.sissi.pipeline.in.presence.state;

import com.sissi.pipeline.in.MatchClass;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-4
 */
public class PresenceStateMatcher extends MatchClass {

	public PresenceStateMatcher() {
		super(Presence.class);
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.getType() == null;
	}
}
