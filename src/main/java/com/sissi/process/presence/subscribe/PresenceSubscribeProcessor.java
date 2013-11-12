package com.sissi.process.presence.subscribe;

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
public class PresenceSubscribeProcessor implements Processor {

	private Addressing addressing;

	private RelationContext relationContext;

	public PresenceSubscribeProcessor(Addressing addressing, RelationContext relationContext) {
		super();
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		if (this.iSubscribeAlready(context, protocol)) {
			return;
		}
		Presence presence = Presence.class.cast(protocol);
		presence.setFrom(context.jid().asStringWithNaked());
		this.writeIfOnline(presence, new User(presence.getTo()));
	}

	private void writeIfOnline(Presence presence, JID to) {
		if (this.addressing.online(to)) {
			Context toContext = this.addressing.find(new User(presence.getTo()));
			if (toContext != null) {
				toContext.write(presence);
			}
		}
	}

	private boolean iSubscribeAlready(Context context, Protocol protocol) {
		return this.relationContext.ourRelation(context.jid(), new User(protocol.getTo())) == null;
	}
}
