package com.sissi.pipeline.in.iq.roster.remove;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Roster;

/**
 * 删除订阅关系
 * 
 * @author kim 2013-11-17
 */
public class RosterRemoveProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.remove(context.jid(), super.build(protocol.cast(Roster.class).first().getJid()));
		return true;
	}
}
