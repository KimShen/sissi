package com.sissi.process.presence.state;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-5
 */
public class PresenceStateMatcher implements Matcher {

	@Override
	public Boolean match(Protocol protocol) {
		return protocol.getType() == null;
	}
}
