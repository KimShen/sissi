package com.sissi.pipeline.process.presence.unsubscribed;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.Relation.State;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-5
 */
public class PresenceUnSubscribedProcessor implements ProcessPipeline {

	private JIDBuilder jidBuilder;

	private Addressing addressing;

	private RelationContext relationContext;

	public PresenceUnSubscribedProcessor(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		this.relationContext.unsubscribed(this.jidBuilder.build(protocol.getTo()), context.jid());
		this.writeIfOnline(context, protocol);
		return false;
	}

	private void writeIfOnline(JIDContext context, Protocol protocol) {
		Presence presence = Presence.class.cast(protocol);
		presence.setFrom(context.jid().asStringWithBare());
		presence.setType(State.UNSUBSCRIBED.toString());
		JID to = this.jidBuilder.build(presence.getTo());
		if (this.addressing.isOnline(to)) {
			this.addressing.find(to).write(presence);
		}
	}
}
