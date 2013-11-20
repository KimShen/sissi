package com.sissi.pipeline.process.message.p2g;

import com.sissi.context.JID;
import com.sissi.context.impl.User;
import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class MessageP2GMatcher implements ProcessPipelineMatcher {

	private JID jid;

	public MessageP2GMatcher(JID jid) {
		super();
		this.jid = jid;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return jid.host().equals(new User(protocol.getTo()).host());
	}
}
