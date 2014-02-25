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
import com.sissi.ucenter.MucStatusCollector;
import com.sissi.ucenter.MucStatusComputer;
import com.sissi.ucenter.MucStatusJudge;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucLeave2AllProcessor extends ProxyProcessor {

	private final MucGroupContext mucGroupContext;

	private final MucStatusComputer mucStatusComputer = new NothingMucStatusComputer();

	public PresenceMucLeave2AllProcessor(MucGroupContext mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Presence presence = new Presence();
		JID group = super.build(protocol.getTo());
		RelationMuc ourRelation = super.ourRelation(context.jid(), group).cast(RelationMuc.class);
		for (Relation each : super.myRelations(group)) {
			JID to = super.build(each.cast(RelationMuc.class).getJID());
			super.findOne(to, true).write(presence.clear().add(new XUser().setItem(new Item(group, to, ourRelation, this.mucGroupContext), this.mucStatusComputer)).setType(PresenceType.UNAVAILABLE).setFrom(protocol.getTo()));
		}
		return true;
	}

	private class NothingMucStatusComputer implements MucStatusComputer {
		@Override
		public NothingMucStatusComputer collect(MucStatusCollector collector, MucStatusJudge judge) {
			return this;
		}
	}
}
