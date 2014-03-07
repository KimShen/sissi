package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.roster.RelationRecover;

/**
 * @author kim 2014年2月19日
 */
public class PresenceInit4SubscribeProcessor implements Input {

	private final RelationRecover relationRecover;

	public PresenceInit4SubscribeProcessor(RelationRecover relationRecover) {
		super();
		this.relationRecover = relationRecover;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.presence() ? true : this.writeAsk(context);
	}

	private boolean writeAsk(JIDContext context) {
		Presence presence = new Presence().setType(PresenceType.SUBSCRIBE);
		for (Relation relation : this.relationRecover.recover(context.jid())) {
			context.write(presence.setFrom(relation.jid()), true);
		}
		return true;
	}
}
