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

	private final MucConfigBuilder mucGroupContext;

	public PresenceMucJoin2SelfPresenceProcessor(MucConfigBuilder mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return false;
//		Presence presence = new Presence();
//		JID group = super.build(protocol.getTo());
//		for (Relation each : super.myRelations(group)) {
//			RelationMuc muc = each.cast(RelationMuc.class);
//			context.write(presence.clear().add(new XUser(context.jid()).setItem(new Item(group, context.jid(), super.build(muc.getJID()), muc, this.mucGroupContext), this.mucStatusComputer)).clauses(super.findOne(super.build(muc.getJID())).status().clauses()).setFrom(group.resource(muc.getName())));
//		}
//		return true;
	}
}
