package com.sissi.pipeline.process.presence.subscribed;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-5
 */
public class PresenceSubscribedProcessor implements ProcessPipeline {

	private JIDBuilder jidBuilder;

	private Addressing addressing;

	private RelationContext relationContext;

	public PresenceSubscribedProcessor(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		JID to = this.jidBuilder.build(protocol.getTo());
		this.relationContext.subscribed(to, context.jid());
		this.writeIfOnline(context, protocol, to);
		return false;
	}

	private void writeIfOnline(JIDContext context, Protocol protocol, JID to) {
		Presence presence = Presence.class.cast(protocol);
		presence.clear();
		presence.setFrom(context.jid().asStringWithBare());
		if (this.addressing.isOnline(to)) {
			this.addressing.find(to).write(presence);
		}
	}
}
