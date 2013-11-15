package com.sissi.pipeline.process.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.relation.RelationContext;
import com.sissi.relation.impl.ItemWrapRelation;

/**
 * @author kim 2013-10-31
 */
public class RosterSetProcessor implements ProcessPipeline {

	private RelationContext relationContext;

	public RosterSetProcessor(RelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		for (Item item : roster.getItem()) {
			this.subscribeEach(context, protocol, item);
		}
		return true;
	}

	private void subscribeEach(JIDContext context, Protocol protocol, Item item) {
		this.relationContext.subscribe(context.jid(), new ItemWrapRelation(item));
	}
}
