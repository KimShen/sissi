package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.context.Status;

/**
 * Presence.type = available, 已从黑名单移除
 * 
 * @author kim 2013年12月6日
 */
public class UnBlock2SelfsPresenceProcessor extends ToSelfsPresenceProcessor {

	@Override
	protected Status build(JIDContext context) {
		return context.status();
	}
}
