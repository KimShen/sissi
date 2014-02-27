package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-10-31
 */
public class RosterSet2SelfsProcessor extends Roster2SelfsItemProcessor {

	public RosterSet2SelfsProcessor(Group group) {
		super(group);
	}

	@Override
	protected RosterSubscription subscription(JID master, JID slave) {
		return RosterSubscription.parse(super.ourRelation(master, slave).cast(RelationRoster.class).getSubscription());
	}

	@Override
	protected boolean ask() {
		return true;
	}

	protected boolean next(String subscription) {
		// None relation continue
		return RosterSubscription.parse(subscription) == RosterSubscription.NONE;
	}
}
