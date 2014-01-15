package com.sissi.pipeline.in.presence.status;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-4
 */
public class PresenceStatus2FansMatcher extends ClassMatcher {

	public PresenceStatus2FansMatcher() {
		super(Presence.class);
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.getType() == null && protocol.getTo() == null;
	}
}
