package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013年12月17日
 */
public class RosterSetCheckLoopProcessor extends ProxyProcessor {

	// Can not add from
	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(NotAllowed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.jid().user(Roster.class.cast(protocol).getFirstItem().getJid()) ? this.writeAndReturn(context, protocol) : true;
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(this.error));
		return false;
	}
}
