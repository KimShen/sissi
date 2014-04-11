package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.MucStatusJudger;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月7日
 */
public class PresenceMucJoin2ReplaceProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	private final MucStatusJudger mucStatusJudger;

	public PresenceMucJoin2ReplaceProcessor(MucConfigBuilder mucConfigBuilder, MucStatusJudger mucStatusJudger) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
		this.mucStatusJudger = mucStatusJudger;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		String newnick = group.resource();
		RelationMuc relation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
		if (relation.activate() && !group.resource().equals(relation.name())) {
			Presence presence = new Presence();
			MucConfig config = this.mucConfigBuilder.build(group);
			for (Relation each : super.myRelations(group)) {
				JID to = super.build(each.cast(RelationMuc.class).jid());
				super.findOne(to, true).write(presence.clear().add(this.mucStatusJudger.judege(new XUser(group, to, config.allowed(to, MucConfig.HIDDEN_NATIVE, null)).item(new Item(config.allowed(to, MucConfig.HIDDEN_COMPUTER, context.jid()), newnick, relation))).cast(XUser.class)).setType(PresenceType.UNAVAILABLE).setFrom(group.resource(relation.name())));
			}
		}
		return true;
	}
}
