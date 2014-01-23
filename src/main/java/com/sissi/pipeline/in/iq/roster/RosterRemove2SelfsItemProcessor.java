package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.GroupAction;

/**
 * @author kim 2013-11-18
 */
public class RosterRemove2SelfsItemProcessor extends Roster2SelfsItemProcessor {

	@Override
	protected String subscription(JID master, JID slave) {
		return GroupAction.REMOVE.toString();
	}

	@Override
	protected Boolean isAsk() {
		return false;
	}

	protected Boolean isNext(String subscription) {
		return true;
	}
}
