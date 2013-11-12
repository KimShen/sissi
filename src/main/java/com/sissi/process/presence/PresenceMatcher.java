package com.sissi.process.presence;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-4
 */
public class PresenceMatcher implements Matcher {

	@Override
	public Boolean match(Protocol protocol) {
		return Presence.class.isAssignableFrom(protocol.getClass());
	}
}
