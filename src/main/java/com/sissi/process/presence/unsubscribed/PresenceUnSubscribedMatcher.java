package com.sissi.process.presence.unsubscribed;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;
import com.sissi.relation.State;

/**
 * @author kim 2013-11-5
 */
public class PresenceUnSubscribedMatcher implements Matcher {

	@Override
	public Boolean match(Protocol protocol) {
		return protocol.getType().equals(State.UNSUBSCRIBED.toString());
	}
}
