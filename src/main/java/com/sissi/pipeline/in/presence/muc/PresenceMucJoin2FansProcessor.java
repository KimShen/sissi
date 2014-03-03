package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2FansProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucGroupContext;

	public PresenceMucJoin2FansProcessor(MucConfigBuilder mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return false;
		// Presence presence = new Presence();
		// JID group = super.build(protocol.getTo());
		// RelationMuc ourRelation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
		// for (Relation each : super.myRelations(group)) {
		// JID to = super.build(each.cast(RelationMuc.class).getJID());
		// super.findOne(to, true).write(presence.clear().add(new XUser(to).setItem(new Item(group, to, context.jid(), ourRelation, this.mucGroupContext), this.mucStatusComputer)).clauses(context.status().clauses()).setFrom(protocol.getTo()));
		// }
		// return true;
	}
}
