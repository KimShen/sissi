package com.sissi.ucenter.muc.impl;

import java.util.List;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.ucenter.muc.MucAffiliationBroadcast;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucItem;

/**
 * @author kim 2014年3月24日
 */
public class ChainedMucAffiliationBroadcast implements MucAffiliationBroadcast {

	private final List<MucAffiliationBroadcast> mucAffiliationBroadcasts;

	public ChainedMucAffiliationBroadcast(List<MucAffiliationBroadcast> mucAffiliationBroadcasts) {
		super();
		this.mucAffiliationBroadcasts = mucAffiliationBroadcasts;
	}

	@Override
	public MucAffiliationBroadcast broadcast(JID jid, JID group, JIDContext invoker, MucItem item, MucConfig config) {
		for (MucAffiliationBroadcast each : this.mucAffiliationBroadcasts) {
			each.broadcast(jid, group, invoker, item, config);
		}
		return this;
	}

}
