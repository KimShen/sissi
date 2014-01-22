package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-11-17
 */
public class RosterRemoveProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		super.update(context.getJid(), super.build(Roster.class.cast(protocol).getFirstItem().getJid()), RosterSubscription.NONE.toString());
		return true;
	}
}
