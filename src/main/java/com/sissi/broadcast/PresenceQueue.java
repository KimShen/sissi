package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013-11-17
 */
public interface PresenceQueue {

	public void offer(JID jid, JID from, JID to, String show, String status, Type type);

	public interface PresenceBuilder {

		public Presence build(JID from, JID to, String show, String status, Type type);
	}
}
