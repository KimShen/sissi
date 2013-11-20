package com.sissi.pipeline.process.presence.state;

import java.util.Set;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.process.presence.notify.PresenceBroadcastProcessor;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-4
 */
public class PresenceFriendsProcessor extends PresenceBroadcastProcessor {

	public PresenceFriendsProcessor(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super(jidBuilder, addressing, relationContext);
	}

	protected void notifyEach(JIDContext context, Presence presence, String to) {
		JIDContext toContext = this.addressing.find(this.jidBuilder.build(to));
		if (toContext != null) {
			presence.setFrom(context.jid().asStringWithBare());
			presence.setTo(to);
			toContext.write(presence);
		}
	}

	@Override
	protected Set<String> generateRelations(JID jid) {
		return this.relationContext.whoSubscribedMe(jid);
	}
}
