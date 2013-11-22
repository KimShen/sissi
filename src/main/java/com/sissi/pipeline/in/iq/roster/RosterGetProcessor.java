package com.sissi.pipeline.in.iq.roster;

import org.springframework.beans.BeanUtils;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.relation.Relation;

/**
 * @author kim 2013-10-31
 */
public class RosterGetProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Roster roster = (Roster) Roster.class.cast(protocol).clear();
		context.write(this.preparpreResponse(protocol).add(this.prepareRelation(context, roster)));
		return false;
	}

	private Roster prepareRelation(JIDContext context, Roster roster) {
		for (Relation each : super.relationContext.myRelations(context.getJid())) {
			Item item = new Item();
			BeanUtils.copyProperties(each, item);
			roster.add(item);
		}
		return roster;
	}

	private IQ preparpreResponse(Protocol protocol) {
		return (IQ)protocol.getParent().reply().clear().setType(Type.RESULT);
	}
}
