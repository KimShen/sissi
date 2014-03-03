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
import com.sissi.ucenter.muc.MucConfigBuilder;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucLeave2AllProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucGroupContext;

	public PresenceMucLeave2AllProcessor(MucConfigBuilder mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return false;
//		Presence presence = new Presence();
//		JID group = super.build(protocol.getTo());
//		RelationMuc ourRelation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
//		for (Relation each : super.myRelations(group)) {
//			JID to = super.build(each.cast(RelationMuc.class).getJID());
//			super.findOne(to, true).write(presence.clear().add(new XUser().setItem(new Item(group, to, ourRelation, this.mucGroupContext), this.mucStatusComputer)).setType(PresenceType.UNAVAILABLE).setFrom(protocol.getTo()));
//		}
//		return true;
	}
}
