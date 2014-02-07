package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013年12月6日
 */
public class Blocked2FansPresenceProcessor extends Block2FansPresenceProcessor {

	private final Presence unavailable = new Presence().setType(PresenceType.UNAVAILABLE);

	@Override
	protected Status build(JIDContext context) {
		return this.unavailable;
	}
}
