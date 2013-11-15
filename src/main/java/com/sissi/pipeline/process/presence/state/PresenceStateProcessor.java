package com.sissi.pipeline.process.presence.state;

import java.util.Set;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-11-14
 */
public abstract class PresenceStateProcessor implements ProcessPipeline {

	protected JIDBuilder jidBuilder;

	protected Addressing addressing;

	protected RelationContext relationContext;

	public PresenceStateProcessor(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		Presence presence = (Presence) this.generatePresence(protocol).clear();
		for (String to : this.generateRelations(context.jid())) {
			this.notifyEach(context, presence, to);
		}
		return true;
	}

	protected Presence generatePresence(Protocol protocol) {
		return protocol != null && Presence.class.isAssignableFrom(protocol.getClass()) ? Presence.class.cast(protocol) : new Presence();
	}

	abstract protected Set<String> generateRelations(JID jid);
	
	abstract protected void notifyEach(JIDContext context, Presence presence, String to);
}