package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.MucGroupContext;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucLeave2AllProcessor extends ProxyProcessor {

	private final MucGroupContext mucGroupContext;

	public PresenceMucLeave2AllProcessor(MucGroupContext mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		RelationMuc ourRelation = RelationMuc.class.cast(super.ourRelation(context.jid(), group));
		Presence presence = new Presence();
		for (Relation each : super.myRelations(group)) {
			RelationMuc relation = RelationMuc.class.cast(each);
			super.findOne(super.build(relation.getJID()), true).write(presence.clear().add(new XUser().add(new Item(group, ourRelation, this.mucGroupContext))).setType(PresenceType.UNAVAILABLE).setFrom(protocol.getTo()));
		}
		return true;
	}
}
