package com.sissi.process.presence;

import com.sissi.context.Context;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.core.Presence;

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
