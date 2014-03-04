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
public class PresenceMucJoin2SelfPresenceProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public PresenceMucJoin2SelfPresenceProcessor(MucConfigBuilder mucGroupContext) {
		super();
		this.mucConfigBuilder = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		JID group = super.build(protocol.getTo());
		for (Relation each : super.myRelations(group)) {
			RelationMuc relation = each.cast(RelationMuc.class);
			context.write(presence.clear().add(new XUser().setItem(new Item(relation, this.mucConfigBuilder.build(group)))).clauses(super.findOne(super.build(relation.getJID()), true).status().clauses()).setFrom(group.resource(relation.getName())));
		}
		return true;
	}
}
