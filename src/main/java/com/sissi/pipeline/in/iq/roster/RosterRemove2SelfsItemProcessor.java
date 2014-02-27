package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013-11-18
 */
public class RosterRemove2SelfsItemProcessor extends Roster2SelfsItemProcessor {

	public RosterRemove2SelfsItemProcessor(Group group) {
		super(group);
	}

	@Override
	protected RosterSubscription subscription(JID master, JID slave) {
		return RosterSubscription.REMOVE;
	}

	@Override
	protected boolean ask() {
		return false;
	}

	protected boolean next(String subscription) {
		return true;
	}
}
