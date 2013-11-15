package com.sissi.pipeline.process.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.impl.User;
import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-5
 */
public class PresenceMUCMatcher implements ProcessPipelineMatcher {

	private JID jid;

	public PresenceMUCMatcher(JID jid) {
		super();
		this.jid = jid;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return jid.host().equals(new User(protocol.getTo()).host());
	}
}
