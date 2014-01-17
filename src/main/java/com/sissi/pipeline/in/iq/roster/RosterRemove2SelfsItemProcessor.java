package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.GroupAction;

/**
 * @author kim 2013-11-18
 */
public class RosterRemove2SelfsItemProcessor extends Roster2SelfsItemProcessor {

	@Override
	protected String getSubscription(JID master, JID slave) {
		return GroupAction.REMOVE.toString();
	}

	protected Boolean getNextWhenThisSubscription(String subscription) {
		return true;
	}
}
