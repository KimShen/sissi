package com.sissi.process.iq.roster;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013-11-4
 */
public class RosterSetMatcher implements Matcher {

	@Override
	public Boolean match(Protocol protocol) {
		return isRoster(protocol) && this.isSetIQ(protocol);
	}

	private boolean isRoster(Protocol protocol) {
		return Roster.class.isAssignableFrom(protocol.getClass());
	}

	private boolean isSetIQ(Protocol protocol) {
		return protocol.hasParent() && Type.SET.toString().equals(protocol.getParent().getType());
	}
}
