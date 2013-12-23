package com.sissi.pipeline.in.iq.block;

import com.sissi.context.JIDContext;
import com.sissi.context.Status;

/**
 * @author kim 2013年12月6日
 */
public class UnBlock2FansPresenceProcessor extends Block2FansPresenceProcessor {

	@Override
	protected Status build(JIDContext context) {
		return context.getStatus();
	}

}
