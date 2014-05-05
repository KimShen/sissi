package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.context.Status;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * Presence.type = unavailable, 已加入黑名单
 * 
 * @author kim 2013年12月6日
 */
public class Blocked2SelfsPresenceProcessor extends ToSelfsPresenceProcessor {

	private final Presence unavailable = new Presence().type(PresenceType.UNAVAILABLE);

	@Override
	protected Status build(JIDContext context) {
		return this.unavailable;
	}
}
