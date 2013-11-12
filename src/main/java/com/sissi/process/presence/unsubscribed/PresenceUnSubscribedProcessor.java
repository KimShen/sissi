package com.sissi.process.presence.unsubscribed;

import com.sissi.addressing.Addressing;
import com.sissi.context.Context;
import com.sissi.context.JID;
import com.sissi.context.user.User;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.RelationContext;
import com.sissi.relation.State;

/**
 * @author kim 2013-11-5
 */
public class PresenceUnSubscribedProcessor implements Processor {

	private Addressing addressing;

	private RelationContext relationContext;

	public PresenceUnSubscribedProcessor(Addressing addressing, RelationContext relationContext) {
		super();
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		this.relationContext.unsubscribed(new User(protocol.getTo()), context.jid());
		this.writeIfOnline(context, protocol);
	}

	private void writeIfOnline(Context context, Protocol protocol) {
		Presence presence = Presence.class.cast(protocol);
		presence.setFrom(context.jid().asStringWithNaked());
		presence.setType(State.UNSUBSCRIBED.toString());
		JID to = new User(presence.getTo());
		if (this.addressing.online(to)) {
			this.addressing.find(to).write(presence);
		}
	}
}
