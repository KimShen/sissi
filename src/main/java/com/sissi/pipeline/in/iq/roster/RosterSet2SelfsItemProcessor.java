package com.sissi.pipeline.in.iq.roster;

import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-10-31
 */
public class RosterSet2SelfsItemProcessor extends Roster2SelfsItemProcessor {

	@Override
	protected String getSubscription() {
		return RosterSubscription.NONE.toString();
	}
}
