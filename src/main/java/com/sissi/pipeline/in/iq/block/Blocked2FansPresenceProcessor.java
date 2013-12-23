package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013年12月6日
 */
public class Blocked2FansPresenceProcessor extends Block2FansPresenceProcessor {

	@Override
	protected Status build(JIDContext context) {
		return new Presence().setType(Type.UNAVAILABLE);
	}
}
