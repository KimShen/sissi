package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-11-18
 */
public class RosterRemove2SelfsItemProcessor extends Roster2SelfsItemProcessor {

	@Override
	protected RosterSubscription subscription(JID master, JID slave) {
		return RosterSubscription.REMOVE;
	}

	@Override
	protected boolean isAsk() {
		return false;
	}

	protected boolean isNext(String subscription) {
		return true;
	}
}
