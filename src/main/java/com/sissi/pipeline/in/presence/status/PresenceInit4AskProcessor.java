package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationRecover;

/**
 * @author kim 2014年2月19日
 */
public class PresenceInit4AskProcessor implements Input {

	private final RelationRecover relationRecover;

	public PresenceInit4AskProcessor(RelationRecover relationRecover) {
		super();
		this.relationRecover = relationRecover;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.presented() ? true : this.writeAsk(context);
	}

	private Boolean writeAsk(JIDContext context) {
		Presence presence = new Presence();
		for (Relation relation : this.relationRecover.recover(context.jid())) {
			context.write(presence.clear().setType(PresenceType.SUBSCRIBE).setFrom(relation.getJID()), true);
		}
		return true;
	}
}
