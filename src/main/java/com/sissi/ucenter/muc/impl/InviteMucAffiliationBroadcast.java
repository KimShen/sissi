package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.Invite;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.muc.MucAffiliationBroadcast;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucItem;

/**
 * @author kim 2014年3月24日
 */
public class InviteMucAffiliationBroadcast implements MucAffiliationBroadcast {

	private final Input proxy;

	public InviteMucAffiliationBroadcast(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public MucAffiliationBroadcast broadcast(JID jid, JID group, JIDContext invoker, MucItem item, MucConfig config) {
		if (!config.allowed(jid, MucConfig.AFFILIATION_CHECK, null) && ItemAffiliation.parse(item.getAffiliation()).contains(config.pull(MongoConfig.FIELD_AFFILIATION, String.class))) {
			this.proxy.input(invoker, new Message().setUser(new XUser().invite(new Invite().setTo(item.getJid()))).setTo(group));
		}
		return this;
	}
}
