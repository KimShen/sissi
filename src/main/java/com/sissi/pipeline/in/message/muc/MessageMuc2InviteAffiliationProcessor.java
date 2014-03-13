package com.sissi.pipeline.in.message.muc;

import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.muc.MucAffiliationBuilder;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月9日
 */
public class MessageMuc2InviteAffiliationProcessor extends ProxyProcessor {

	private final MucAffiliationBuilder mucAffiliationBuilder;

	private final MucConfigBuilder mucConfigBuilder;

	public MessageMuc2InviteAffiliationProcessor(MucAffiliationBuilder mucAffiliationBuilder, MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucAffiliationBuilder = mucAffiliationBuilder;
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		return config.allowed(context.jid(), MucConfig.AFFILIATION_EXISTS, null) ? this.writeAndReturn(super.build(protocol.cast(Message.class).getUser().getInvite().getTo()), group, config) : true;
	}

	private boolean writeAndReturn(JID jid, JID group, MucConfig config) {
		this.mucAffiliationBuilder.build(group).approve(jid, config.pull(MongoConfig.FIELD_AFFILIATION, String.class));
		return true;
	}
}
