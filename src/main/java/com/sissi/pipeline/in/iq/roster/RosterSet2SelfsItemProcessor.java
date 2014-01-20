package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-10-31
 */
public class RosterSet2SelfsItemProcessor extends Roster2SelfsItemProcessor {

	@Override
	protected String subscription(JID master, JID slave) {
		return super.ourRelation(master, slave).getSubscription();
	}

	protected Boolean isNext(String subscription) {
		return RosterSubscription.parse(subscription) == RosterSubscription.NONE;
	}
}
