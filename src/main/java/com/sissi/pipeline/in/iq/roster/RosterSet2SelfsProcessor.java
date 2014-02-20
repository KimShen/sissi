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
		return RosterSubscription.parse(RelationRoster.class.cast(super.ourRelation(master, slave)).getSubscription());
	}

	@Override
	protected boolean isAsk() {
		return true;
	}

	protected boolean isNext(String subscription) {
		return RosterSubscription.parse(subscription) == RosterSubscription.NONE;
	}
}
