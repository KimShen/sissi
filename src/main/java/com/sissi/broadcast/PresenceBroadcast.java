package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.context.JIDContext.Status;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-17
 */
public interface PresenceBroadcast {

	public PresenceBroadcast offer(JID jid, JID from, JID to, Status status);

	public interface PresenceBuilder {

		public Protocol build(JID from, JID to, Status status);
	}
}
