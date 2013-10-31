package com.sisi.process.presence;

import com.sisi.context.Context;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.core.Presence;

/**
 * @author kim 2013-10-24
 */
public class PresenceProcessor implements Processor {

	@Override
	public Protocol process(Context context, Protocol protocol) {
		return null;
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return Presence.class.isAssignableFrom(protocol.getClass());
	}

}
