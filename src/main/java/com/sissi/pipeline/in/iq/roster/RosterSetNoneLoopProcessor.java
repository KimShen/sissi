package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月17日
 */
public class RosterSetNoneLoopProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return !context.getJid().getUser().equals(super.build(Roster.class.cast(protocol).getFirstItem().getJid()).getUser());
	}

}
