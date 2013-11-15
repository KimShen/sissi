package com.sissi.pipeline.process.presence.state;

import java.util.Set;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.context.impl.User;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-4
 */
public class PresenceMyselfProcessor extends PresenceStateProcessor {

	public PresenceMyselfProcessor(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super(jidBuilder, addressing, relationContext);
	}

	protected Set<String> generateRelations(JID jid) {
		return this.relationContext.iSubscribedWho(jid);
	}

	@Override
	protected void notifyEach(JIDContext context, Presence presence, String to) {
		if (super.addressing.isOnline(new User(to))) {
			presence.setFrom(to);
			presence.setTo(context.jid().asStringWithBare());
			context.write(presence);
		}
	}
}
