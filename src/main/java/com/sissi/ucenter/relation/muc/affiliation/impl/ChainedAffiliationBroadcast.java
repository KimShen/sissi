package com.sissi.ucenter.relation.muc.affiliation.impl;

import java.util.List;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.ucenter.relation.muc.MucItem;
import com.sissi.ucenter.relation.muc.affiliation.AffiliationBroadcast;
import com.sissi.ucenter.relation.muc.room.Room;

/**
 * @author kim 2014年3月24日
 */
public class ChainedAffiliationBroadcast implements AffiliationBroadcast {

	private final List<AffiliationBroadcast> affiliationBroadcasts;

	public ChainedAffiliationBroadcast(List<AffiliationBroadcast> affiliationBroadcasts) {
		super();
		this.affiliationBroadcasts = affiliationBroadcasts;
	}

	@Override
	public AffiliationBroadcast broadcast(JID group, Room room, MucItem item, JIDContext invoker) {
		for (AffiliationBroadcast each : this.affiliationBroadcasts) {
			each.broadcast(group, room, item, invoker);
		}
		return this;
	}
}
