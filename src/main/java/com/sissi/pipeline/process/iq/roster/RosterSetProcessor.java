package com.sissi.pipeline.process.iq.roster;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDContext;
import com.sissi.context.impl.User;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.presence.Presence;
import com.sissi.relation.RelationContext;
import com.sissi.relation.impl.ItemWrapRelation;

/**
 * @author kim 2013-10-31
 */
public class RosterSetProcessor implements ProcessPipeline {

	private Addressing addressing;

	private RelationContext relationContext;

	private ProcessPipeline presencProcessor;

	public RosterSetProcessor(Addressing addressing, RelationContext relationContext, ProcessPipeline presencProcessor) {
		super();
		this.addressing = addressing;
		this.relationContext = relationContext;
		this.presencProcessor = presencProcessor;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		Presence presence = new Presence();
		for (Item item : roster.getItem()) {
			this.subscribeEach(context, presence, protocol, item);
		}
		return true;
	}

	private void subscribeEach(JIDContext context, Presence presence, Protocol protocol, Item item) {
		this.relationContext.subscribe(context.jid(), new ItemWrapRelation(item));
		presence.setTo(item.getJid());
		this.presencProcessor.process(this.addressing.find(new User(item.getJid())), presence);
	}
}
