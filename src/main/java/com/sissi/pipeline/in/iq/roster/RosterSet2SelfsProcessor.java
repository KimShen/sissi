package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-10-31
 */
public class RosterSet2SelfsProcessor extends Roster2SelfsItemProcessor {

	@Override
	protected RosterSubscription subscription(JID master, JID slave) {
		return RosterSubscription.parse(super.ourRelation(master, slave).getSubscription());
	}

	@Override
	protected boolean isAsk() {
		return true;
	}

	protected boolean isNext(String subscription) {
		return RosterSubscription.parse(subscription) == RosterSubscription.NONE;
	}
}
