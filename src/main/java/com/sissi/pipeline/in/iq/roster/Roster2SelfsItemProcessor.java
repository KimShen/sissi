package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月12日
 */
abstract class Roster2SelfsItemProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Roster.class.cast(protocol).getFirstItem().setSubscription(this.build());
		super.offer(context.getJid(), protocol.getParent());
		return true;
	}

	abstract protected String build();
}
