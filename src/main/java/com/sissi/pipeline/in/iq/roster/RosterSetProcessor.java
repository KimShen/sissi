package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.relation.ItemWrapRelation;

/**
 * @author kim 2013-10-31
 */
public class RosterSetProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		// convert jid to bare jid
		super.establish(context.getJid(), new ItemWrapRelation(super.jidBuilder, Roster.class.cast(protocol).getFirstItem()));
		return true;
	}
}
