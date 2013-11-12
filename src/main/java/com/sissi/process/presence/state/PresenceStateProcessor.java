package com.sissi.process.presence.state;

import com.sissi.addressing.Addressing;
import com.sissi.context.Context;
import com.sissi.context.user.User;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-4
 */
public class PresenceStateProcessor implements Processor {

	private Addressing addressing;

	private RelationContext relationContext;

	public PresenceStateProcessor(Addressing addressing, RelationContext relationContext) {
		super();
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		Presence presence = (Presence) protocol;
		presence.clear();
		presence.setFrom(context.jid().asStringWithNaked());
		for (String jid : this.relationContext.whoSubscribedMe(context.jid())) {
			this.notifyEach(presence, jid);
		}
	}

	private void notifyEach(Presence presence, String jid) {
		Context toContext = this.addressing.find(new User(jid));
		if (toContext != null) {
			toContext.write(presence);
		}
	}
}
