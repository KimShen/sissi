package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.context.Status;
import com.sissi.protocol.presence.Presence;

/**
 * 出席构造器
 * 
 * @author kim 2013年12月23日
 */
public interface PresenceBuilder {

	/**
	 * @param from Presence.from
	 * @param status 出席状态
	 * @return
	 */
	public Presence build(JID from, Status status);
}