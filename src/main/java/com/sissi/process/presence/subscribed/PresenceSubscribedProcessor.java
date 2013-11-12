package com.sissi.process.presence.subscribed;

import com.sissi.addressing.Addressing;
import com.sissi.context.Context;
import com.sissi.context.JID;
import com.sissi.context.user.User;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-5
 */
public class PresenceSubscribedProcessor implements Processor {

	private Addressing addressing;

	private RelationContext relationContext;

	public PresenceSubscribedProcessor(Addressing addressing, RelationContext relationContext) {
		super();
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		JID to = new User(protocol.getTo());
		this.relationContext.subscribed(to, context.jid());
		this.writeIfOnline(context, protocol, to);
	}

	private void writeIfOnline(Context context, Protocol protocol, JID to) {
		Presence presence = Presence.class.cast(protocol);
		presence.clear();
		presence.setFrom(context.jid().asStringWithNaked());
		if (this.addressing.online(to)) {
			this.addressing.find(to).write(presence);
		}
	}
}
