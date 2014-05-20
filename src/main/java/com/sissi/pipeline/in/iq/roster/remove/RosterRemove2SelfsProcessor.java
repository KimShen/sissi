package com.sissi.pipeline.in.iq.roster.remove;

import com.sissi.context.JID;
import com.sissi.pipeline.in.iq.roster.RosterItemProcessor;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * Roster推送
 * 
 * @author kim 2013-11-18
 */
public class RosterRemove2SelfsProcessor extends RosterItemProcessor {

	public RosterRemove2SelfsProcessor(Group group) {
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
}
