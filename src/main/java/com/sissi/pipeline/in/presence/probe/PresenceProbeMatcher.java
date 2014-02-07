package com.sissi.pipeline.in.presence.probe;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-11-4
 */
public class PresenceProbeMatcher extends ClassMatcher {

	public PresenceProbeMatcher() {
		super(Presence.class);
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && PresenceType.PROBE.equals(protocol.getType());
	}
}
