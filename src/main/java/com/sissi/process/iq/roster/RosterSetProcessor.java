package com.sissi.process.iq.roster;

import com.sissi.context.Context;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.relation.RelationContext;
import com.sissi.relation.impl.ItemWrapRelation;

/**
 * @author kim 2013-10-31
 */
public class RosterSetProcessor implements Processor {

	private RelationContext relationContext;

	private Processor iqResultProcessor;

	public RosterSetProcessor(RelationContext relationContext, Processor iqResultProcessor) {
		super();
		this.relationContext = relationContext;
		this.iqResultProcessor = iqResultProcessor;
	}

	@Override
	public void process(Context context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		for (Item item : roster.getItem()) {
			this.subscribeEach(context, protocol, item);
		}
	}

	private void subscribeEach(Context context, Protocol protocol, Item item) {
		this.relationContext.subscribe(context.jid(), new ItemWrapRelation(item));
		this.iqResultProcessor.process(context, protocol);
	}
}
