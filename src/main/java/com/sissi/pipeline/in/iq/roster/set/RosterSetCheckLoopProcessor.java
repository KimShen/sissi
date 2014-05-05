package com.sissi.pipeline.in.iq.roster.set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.iq.roster.Roster;

/**
 * 回路校验
 * 
 * @author kim 2013年12月17日
 */
public class RosterSetCheckLoopProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(NotAllowed.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.jid().like(protocol.cast(Roster.class).getFirstItem().getJid()) ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
