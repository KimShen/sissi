package com.sissi.pipeline.in.presence.init;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.roster.RelationAck;

/**
 * 离线邀请
 * 
 * @author kim 2014年2月19日
 */
public class PresenceInit4SubscribeProcessor implements Input {

	private final RelationAck ack;

	public PresenceInit4SubscribeProcessor(RelationAck ack) {
		super();
		this.ack = ack;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.writeAsk(context);
	}

	private boolean writeAsk(JIDContext context) {
		Presence presence = new Presence().type(PresenceType.SUBSCRIBE);
		for (Relation relation : this.ack.ack(context.jid())) {
			context.write(presence.setFrom(relation.jid()), true);
		}
		return true;
	}
}
