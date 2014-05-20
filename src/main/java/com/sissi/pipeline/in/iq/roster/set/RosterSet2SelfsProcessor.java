package com.sissi.pipeline.in.iq.roster.set;

import com.sissi.context.JID;
import com.sissi.pipeline.in.iq.roster.RosterItemProcessor;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * Roster推送
 * 
 * @author kim 2013-10-31
 */
public class RosterSet2SelfsProcessor extends RosterItemProcessor {

	public RosterSet2SelfsProcessor(Group group) {
		super(group);
	}

	@Override
	protected RosterSubscription subscription(JID master, JID slave) {
		return RosterSubscription.parse(super.ourRelation(master, slave).cast(RosterRelation.class).subscription());
	}

	@Override
	protected boolean ask() {
		return true;
	}
}
