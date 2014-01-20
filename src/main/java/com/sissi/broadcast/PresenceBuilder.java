package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.context.Status;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月23日
 */
public interface PresenceBuilder {

	public Protocol build(JID from, Status status);
}