package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucStatusJudger;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin4FansProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	private final MucStatusJudger mucStatusJudger;

	public PresenceMucJoin4FansProcessor(MucConfigBuilder mucGroupContext, MucStatusJudger mucStatusJudger) {
		super();
		this.mucConfigBuilder = mucGroupContext;
		this.mucStatusJudger = mucStatusJudger;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		JID group = super.build(protocol.getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		for (Relation each : super.myRelations(group)) {
			RelationMuc relation = each.cast(RelationMuc.class);
			JID to = super.build(relation.jid());
			context.write(presence.clear().add(this.mucStatusJudger.judege(new XUser(context.jid(), config.allowed(context.jid(), MucConfig.HIDDEN_NATIVE, null)).item(new Item(config.allowed(context.jid(), MucConfig.HIDDEN_COMPUTER, to), relation))).cast(XUser.class)).clauses(super.findOne(to, true).status().clauses()).setFrom(group.resource(relation.name())));
		}
		return true;
	}
}
