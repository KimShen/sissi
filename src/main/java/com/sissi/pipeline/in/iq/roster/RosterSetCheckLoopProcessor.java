package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月17日
 */
public class RosterSetCheckLoopProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return !context.getJid().getUser().equals(super.build(Roster.class.cast(protocol).getFirstItem().getJid()).getUser()) ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		// Can not add from
		context.write(protocol.getParent().reply().setError(new ServerError().setType(ProtocolType.CANCEL).add(NotAllowed.DETAIL)));
		return false;
	}
}
