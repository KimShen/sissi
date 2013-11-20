package com.sissi.pipeline.process.presence.subscribe;

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
public class PresenceSubscribeProcessor implements ProcessPipeline {

	private JIDBuilder jidBuilder;

	private Addressing addressing;

	private RelationContext relationContext;

	public PresenceSubscribeProcessor(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		if (this.iSubscribeAlready(context, protocol)) {
			return false;
		}
		Presence presence = Presence.class.cast(protocol);
		presence.setFrom(context.jid().asStringWithBare());
		this.writeIfOnline(presence, this.jidBuilder.build(presence.getTo()));
		return false;
	}

	private void writeIfOnline(Presence presence, JID to) {
		if (this.addressing.isOnline(to)) {
			JIDContext toContext = this.addressing.find(this.jidBuilder.build(presence.getTo()));
			if (toContext != null) {
				toContext.write(presence);
			}
		}
	}

	private boolean iSubscribeAlready(JIDContext context, Protocol protocol) {
		return this.relationContext.ourRelation(context.jid(), this.jidBuilder.build(protocol.getTo())) == null;
	}
}
