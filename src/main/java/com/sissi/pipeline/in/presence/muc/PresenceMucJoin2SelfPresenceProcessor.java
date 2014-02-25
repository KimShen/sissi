package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.MucGroupContext;
import com.sissi.ucenter.MucStatusComputer;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfPresenceProcessor extends ProxyProcessor {

	private final MucStatusComputer mucStatusComputer;

	private final MucGroupContext mucGroupContext;

	public PresenceMucJoin2SelfPresenceProcessor(MucStatusComputer mucStatusComputer, MucGroupContext mucGroupContext) {
		super();
		this.mucStatusComputer = mucStatusComputer;
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		JID group = super.build(protocol.getTo());
		for (Relation each : super.myRelations(group)) {
			RelationMuc muc = each.cast(RelationMuc.class);
			context.write(presence.clear().add(new XUser(context.jid()).setItem(new Item(group, context.jid(), super.build(muc.getJID()), muc, this.mucGroupContext), this.mucStatusComputer)).clauses(super.findOne(super.build(muc.getJID())).status().clauses()).setFrom(group.resource(muc.getName())));
		}
		return true;
	}
}
