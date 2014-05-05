package com.sissi.broadcast;

import com.sissi.context.JID;
import com.sissi.context.Status;

/**
 * 出席广播
 * 
 * @author kim 2013-11-17
 */
public interface BroadcastPresence {

	/**
	 * @param jid 目标JID
	 * @param from 来源JID
	 * @param status 出席状态
	 * @return
	 */
	public BroadcastPresence broadcast(JID jid, JID from, Status status);
}
