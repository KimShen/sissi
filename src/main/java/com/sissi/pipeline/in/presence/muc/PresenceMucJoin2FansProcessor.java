package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.MucGroupContext;
import com.sissi.ucenter.muc.MucStatusComputer;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2FansProcessor extends ProxyProcessor {

	private final MucGroupContext mucGroupContext;

	private final MucStatusComputer mucStatusComputer;

	public PresenceMucJoin2FansProcessor(MucGroupContext mucGroupContext, MucStatusComputer mucStatusComputer) {
		super();
		this.mucGroupContext = mucGroupContext;
		this.mucStatusComputer = mucStatusComputer;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		JID group = super.build(protocol.getTo());
		RelationMuc ourRelation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
		for (Relation each : super.myRelations(group)) {
			JID to = super.build(each.cast(RelationMuc.class).getJID());
			super.findOne(to, true).write(presence.clear().add(new XUser(to).setItem(new Item(group, to, context.jid(), ourRelation, this.mucGroupContext), this.mucStatusComputer)).clauses(context.status().clauses()).setFrom(protocol.getTo()));
		}
		return true;
	}
}
