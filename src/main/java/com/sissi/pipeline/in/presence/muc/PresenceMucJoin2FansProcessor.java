package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2FansProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public PresenceMucJoin2FansProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		JID group = super.build(protocol.getTo());
		RelationMuc relation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
		for (Relation each : super.myRelations(group)) {
			super.findOne(super.build(each.getJID()), true).write(presence.clear().add(new XUser().setItem(new Item(relation, this.mucConfigBuilder.build(group)))).clauses(context.status().clauses()).setFrom(protocol.getTo()));
		}
		return true;
	}
}
