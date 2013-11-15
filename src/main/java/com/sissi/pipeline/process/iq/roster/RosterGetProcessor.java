package com.sissi.pipeline.process.iq.roster;

import org.springframework.beans.BeanUtils;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.relation.Relation;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-10-31
 */
public class RosterGetProcessor implements ProcessPipeline {

	private RelationContext relationContext;

	public RosterGetProcessor(RelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		Roster roster = Roster.class.cast(protocol);
		roster.clear();
		this.prepareSubscribes(context, roster);
		context.write(this.prepareIQ(protocol, roster));
		return false;
	}

	private IQ prepareIQ(Protocol protocol, Roster roster) {
		IQ iq = (IQ) protocol.getParent().reply();
		iq.clear();
		iq.setType(Type.RESULT.toString());
		iq.add(roster);
		return iq;
	}

	private void prepareSubscribes(JIDContext context, Roster roster) {
		for (Relation each : this.relationContext.myRelations(context.jid())) {
			Item item = new Item();
			BeanUtils.copyProperties(each, item);
			// TODO to compute relation
			item.setSubscription("both");
			roster.add(item);
		}
	}
}
