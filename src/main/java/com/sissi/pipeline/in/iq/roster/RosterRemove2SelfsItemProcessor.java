package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013-11-18
 */
public class RosterRemove2SelfsItemProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Roster.class.cast(protocol).getFirstItem().setSubscription(Item.Action.REMOVE.toString());
		super.protocolQueue.offer(context.getJid(), protocol.getParent());
		return true;
	}
}
