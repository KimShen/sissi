package com.sissi.ucenter.relation.muc.affiliation;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.ucenter.relation.muc.MucItem;
import com.sissi.ucenter.relation.muc.room.Room;

/**
 * 岗位变更广播
 * 
 * @author kim 2014年3月24日
 */
public interface AffiliationBroadcast {

	/**
	 * @param group
	 * @param room
	 * @param item
	 * @param invoker 发起人
	 * @return
	 */
	public AffiliationBroadcast broadcast(JID group, Room room, MucItem item, JIDContext invoker);
}
