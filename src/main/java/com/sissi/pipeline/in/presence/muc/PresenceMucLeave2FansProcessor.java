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
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucLeave2FansProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public PresenceMucLeave2FansProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		JID group = super.build(protocol.getTo());
		MucConfig config = this.mucConfigBuilder.build(group);
		RelationMuc relation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
		for (Relation each : super.myRelations(group)) {
			JID to = super.build(each.cast(RelationMuc.class).getJID());
			super.findOne(to, true).write(presence.clear().add(new XUser().setItem(new Item(config.allowed(to, MucConfig.HIDDEN_COMPUTER, context.jid()), relation, this.mucConfigBuilder.build(group)))).setType(PresenceType.UNAVAILABLE).setFrom(protocol.getTo()));
		}
		return true;
	}
}
