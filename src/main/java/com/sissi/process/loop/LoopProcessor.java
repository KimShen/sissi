package com.sissi.process.loop;

import com.sissi.context.Context;
import com.sissi.context.user.User;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-31
 */
public class LoopProcessor implements Processor {
	
	@Override
	public Protocol process(Context context, Protocol protocol) {
		return null;
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		if (this.hasJID(protocol.getTo())) {
			return new User(protocol.getTo()).loop();
		} else {
			return false;
		}
	}

	private boolean hasJID(String jid) {
		return jid != null && !jid.isEmpty();
	}
}
