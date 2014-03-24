package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.muc.MucAffiliationBroadcast;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月14日
 */
public class MucSetBroadcastAffiliationProcessor extends ProxyProcessor {

	private final MucAffiliationBroadcast mucAffiliationBroadcast;

	private final MucConfigBuilder mucConfigBuilder;

	public MucSetBroadcastAffiliationProcessor(MucAffiliationBroadcast mucAffiliationBroadcast, MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucAffiliationBroadcast = mucAffiliationBroadcast;
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			this.mucAffiliationBroadcast.broadcast(super.build(item.getJid()), item.group(group), context, item, config);
		}
		return true;
	}
}
