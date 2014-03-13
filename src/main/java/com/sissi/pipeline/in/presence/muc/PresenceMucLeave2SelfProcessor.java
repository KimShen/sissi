package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucStatusJudger;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucLeave2SelfProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	private final MucStatusJudger mucStatusJudger;

	public PresenceMucLeave2SelfProcessor(MucConfigBuilder mucConfigBuilder, MucStatusJudger mucStatusJudger) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
		this.mucStatusJudger = mucStatusJudger;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		RelationMuc relation = super.ourRelation(context.jid(), group).cast(RelationMuc.class).noneRole();
		context.write(new Presence().add(this.mucStatusJudger.judege(new XUser(context.jid(), config.allowed(context.jid(), MucConfig.HIDDEN_NATIVE, null)).setItem(new Item(config.allowed(context.jid(), MucConfig.HIDDEN_COMPUTER, context.jid()), relation))).cast(XUser.class)).setType(PresenceType.UNAVAILABLE).setFrom(protocol.getTo()));
		return true;
	}
}
