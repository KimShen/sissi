package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013-10-31
 */
public class RosterSetAndUnicastProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Roster.class.cast(protocol).getFirstItem().setSubscription(Roster.Subscription.NONE.toString());
		super.addressing.findOne(context.getJid().getBare()).write(protocol.getParent());
		return true;
	}
}
