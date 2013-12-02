package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.context.MyPresence;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-17
 */
public interface PresenceBroadcast {

	public void offer(JID jid, JID from, JID to, MyPresence presence);

	public interface PresenceBuilder {

		public Protocol build(JID from, JID to, MyPresence presence);
	}
}
